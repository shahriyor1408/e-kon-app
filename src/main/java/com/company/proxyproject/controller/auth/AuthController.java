package com.company.proxyproject.controller.auth;

import com.company.proxyproject.dto.auth.LoginDto;
import com.company.proxyproject.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.company.proxyproject.constants.AppConstants.API_VERSION;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 17/08/23 14:38
 * proxy-project
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + "/auth")
public class AuthController {

    private final AuthService authService; // password +3oh2MISc7

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

//    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto, BindingResult result) throws MethodArgumentNotValidException {
//        return authService.register(registerDto, result);
//    }

//    @GetMapping("/session-user")
//    public ResponseEntity<?> getSessionUser() {
//        return authService.getSessionUser();
//    }
//
//    @PostMapping(value = "/logout")
//    public ResponseEntity<?> logout() {
//        return authService.logout();
//    }
}
