package com.company.proxyproject.exception;

import com.company.proxyproject.dto.response.ResponseData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;

@Getter
@Setter
public class CustomAuthenticationException extends AuthenticationException {
    private ResponseEntity<?> response;

    public CustomAuthenticationException(String msg) {
        super(msg);
    }

    public CustomAuthenticationException(ResponseEntity<?> response) {
        super(Objects.requireNonNull((ResponseData<?>) response.getBody()).getMessage());
        this.response = response;
    }

    public ResponseEntity<?> getResponse() {
        return response;
    }
}
