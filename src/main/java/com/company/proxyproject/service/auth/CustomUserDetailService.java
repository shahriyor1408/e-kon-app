package com.company.proxyproject.service.auth;

import com.company.proxyproject.common.MessageSingleton;
import com.company.proxyproject.entity.auth.User;
import com.company.proxyproject.repository.auth.AuthRepository;
import com.company.proxyproject.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MessageSingleton messageSingleton;
    private final AuthRepository userRepository;

    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username).
                orElseThrow(() -> new UsernameNotFoundException(Objects.requireNonNull(
                        messageSingleton.userDoesNotExist().getBody()).getMessage()));
        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(new HashSet<>())
                .isEnabled(true)
                .status(user.getStatus())
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .user(user)
                .build();
    }
}
