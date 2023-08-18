package com.company.proxyproject.service.auth;

import com.company.proxyproject.common.MessageSingleton;
import com.company.proxyproject.common.PasswordGeneratorHelper;
import com.company.proxyproject.common.SecurityProvider;
import com.company.proxyproject.constants.enums.Status;
import com.company.proxyproject.dto.auth.LoginDto;
import com.company.proxyproject.dto.auth.RegisterDto;
import com.company.proxyproject.dto.auth.UserDto;
import com.company.proxyproject.dto.response.TokenResponseDto;
import com.company.proxyproject.entity.auth.Session;
import com.company.proxyproject.entity.auth.User;
import com.company.proxyproject.repository.auth.AuthRepository;
import com.company.proxyproject.repository.auth.SessionRepository;
import com.company.proxyproject.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final MessageSingleton messageSingleton;
    private final JwtTokenService jwtTokenService;
    private final SessionRepository sessionRepository;
    private final PasswordGeneratorHelper passwordGeneratorHelper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public ResponseEntity<?> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        Optional<User> userOptional = authRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return messageSingleton.userDoesNotExist();
        }
        User user = userOptional.get();
        Optional<Session> sessionOptional = sessionRepository.findSessionByUserId(user.getId());
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            session.setStatus(Status.ACTIVE);
            Session save = sessionRepository.save(session);
            return messageSingleton.success(new TokenResponseDto(save.getToken(), userDetails.getFullName()));
        }

        String token = jwtTokenService.generateToken(userDetails.getUsername());
        Session session = Session.builder()
                .user(user)
                .token(token)
                .status(Status.ACTIVE)
                .build();
        Session save = sessionRepository.save(session);
        return messageSingleton.success(new TokenResponseDto(save.getToken(), userDetails.getFullName()));
    }

    public ResponseEntity<?> logout() {
        Optional<User> currentUser = getCurrentUser();
        if (currentUser.isPresent()) {
            User user = currentUser.get();
            Optional<Session> sessionOptional = sessionRepository.findSessionByUserId(user.getId());
            if (sessionOptional.isPresent()) {
                Session session = sessionOptional.get();
                session.setStatus(Status.DISABLED);
                sessionRepository.save(session);
            }
        }
        return messageSingleton.success();
    }

    protected Optional<User> getCurrentUser() {
        CustomUserDetails currentUser = SecurityProvider.getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            return authRepository.findById(currentUser.getId());
        }
        return Optional.empty();
    }


    public ResponseEntity<?> getSessionUser() {
        Optional<User> userOpt = getCurrentUser();
        if (userOpt.isEmpty()) {
            return messageSingleton.noDataFound();
        }
        return messageSingleton.success(new UserDto(userOpt.get()));
    }

    public ResponseEntity<?> register(RegisterDto registerDto, BindingResult result) throws MethodArgumentNotValidException {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(null, result);
        }

        Boolean userExistsByUsernameOrEmail = authRepository.existsByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail());
        if (userExistsByUsernameOrEmail) {
            return messageSingleton.dataExists();
        }

        String rawPassword = passwordGeneratorHelper.generatePassword();

        User user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(rawPassword))
                .gender(registerDto.getGender())
                .status(Status.ACTIVE)
                .build();

        User savedUser = authRepository.save(user);
        UserDto userDto = new UserDto(savedUser);
        userDto.setPassword(rawPassword);
        return messageSingleton.success(userDto);
    }
}
