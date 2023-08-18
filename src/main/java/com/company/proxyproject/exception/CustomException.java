package com.company.proxyproject.exception;

import com.company.proxyproject.dto.response.ResponseData;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class CustomException extends RuntimeException {
    private ResponseEntity<?> response;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(ResponseEntity<?> response) {
        super(Objects.requireNonNull((ResponseData<?>) response.getBody()).getMessage());
        this.response = response;
    }

    public ResponseEntity<?> getResponse() {
        return response;
    }
}
