package com.company.proxyproject.service.auth;

import com.company.proxyproject.util.JwtUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    private final JwtUtil jwtUtil;

    private final String tokenSecret;

    public JwtTokenService(JwtUtil jwtUtil, @Value("${jwt.token.secret}") String tokenSecret) {
        this.jwtUtil = jwtUtil;
        this.tokenSecret = tokenSecret;
    }

    public String generateToken(@NonNull String subject) {
        return jwtUtil.jwt(subject, getTokenSecret());
    }

    public Boolean isValid(String token) {
        return jwtUtil.isTokenValid(token, getTokenSecret());
    }

    public String subject(String token) {
        return jwtUtil.getSubject(token, getTokenSecret());
    }

    private String getTokenSecret() {
        return tokenSecret;
    }
}
