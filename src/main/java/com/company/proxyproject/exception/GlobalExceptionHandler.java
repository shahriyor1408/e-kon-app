package com.company.proxyproject.exception;

import com.company.proxyproject.common.GlobalContextHolder;
import com.company.proxyproject.common.MessageSingleton;
import com.company.proxyproject.logging.LogService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import static com.company.proxyproject.util.ErrorUtil.getStacktrace;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSingleton messageSingleton;

    private final LogService logService;

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(final BadCredentialsException e) {
        final String traceId = GlobalContextHolder.getTraceId();
        logService.logException(traceId, getStacktrace(e), "unable to authenticate user");
        return messageSingleton.incorrectPassword();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(final AccessDeniedException e) {
        logService.logException(GlobalContextHolder.getTraceId(), getStacktrace(e), "unauthorized");
        return messageSingleton.unauthorized();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        logService.logException(GlobalContextHolder.getTraceId(), getStacktrace(e), "data integrity violation error");
        return messageSingleton.invalidData();
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(final CustomException e) {
        return e.getResponse();
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<?> handleCustomAuthenticationException(final CustomAuthenticationException e) {
        return e.getResponse();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(final Exception e, final WebRequest webRequest, final HttpServletRequest request) {
        logService.logException(GlobalContextHolder.getTraceId(), getStacktrace(e), "unexpected error");
        log.error("HANDLE_UNEXPECTED_ERROR: ", e);
        return messageSingleton.internalServerError();
    }

    public ResponseEntity<?> handleConstraintViolationException(final ConstraintViolationException e) {
        logService.logException(GlobalContextHolder.getTraceId(), getStacktrace(e), "validation error");

        return messageSingleton.invalidData();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        Map<Object, Object> validation = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    String field = error.getField();
                    validation.put(field, "");
                });
        return messageSingleton.prepareValidationResponse(validation);
    }
}
