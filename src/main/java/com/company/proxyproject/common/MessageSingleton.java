package com.company.proxyproject.common;

import com.company.proxyproject.constants.MessageKey;
import com.company.proxyproject.dto.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.company.proxyproject.util.DateUtil.getTime;

@Component
public class MessageSingleton {

    public ResponseEntity<ResponseData<String>> unauthorized() {
        return prepareResponse(MessageKey.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> forbidden() {
        return prepareResponse(MessageKey.FORBIDDEN, HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<ResponseData<String>> userDoesNotExist() {
        return prepareResponse(MessageKey.USER_DOES_NOT_EXIST, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> accountPending() {
        return prepareResponse(MessageKey.ACCOUNT_PENDING, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> usernameExists() {
        return prepareResponse(MessageKey.USERNAME_EXISTS, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<String>> incorrectPassword() {
        return prepareResponse(MessageKey.INCORRECT_PASSWORD, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> emailExists() {
        return prepareResponse(MessageKey.EMAIL_EXISTS, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<String>> dataExists() {
        return prepareResponse(MessageKey.DATA_EXISTS, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> success(Map<Object, Object> object) {
        return prepareResponse(object, MessageKey.SUCCESS, HttpStatus.OK);
    }

    public ResponseEntity<Object> success(Object object) {
        return prepareResponse(object, MessageKey.SUCCESS);
    }

    public ResponseEntity<Object> accepted(Object object) {
        return prepareResponse(object);
    }

    public ResponseEntity<ResponseData<String>> success() {
        return prepareResponse(MessageKey.SUCCESS, HttpStatus.OK);
    }

    public ResponseEntity<ResponseData<String>> noDataFound() {
        return prepareResponse(MessageKey.DATA_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ResponseData<String>> internalServerError() {
        return prepareResponse(MessageKey.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ResponseData<String>> invalidData() {
        return prepareResponse(MessageKey.INVALID_DATA);
    }

    public ResponseEntity<ResponseData<String>> operationFailed(String message) {
        return prepareResponse(message, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> userNotFoundForStation(String stationName) {
        return prepareResponse(stationName, MessageKey.USER_NOT_EXIST_FOR_STATION);
    }

    private ResponseEntity<ResponseData<String>> prepareResponse(String key, HttpStatus status) {
        ResponseData<String> response;
        response = new ResponseData<>(null, key);
        response.setTimestamp(getTime());
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<Object> prepareValidationResponse(Map<Object, Object> object) {
        return prepareResponse(object, MessageKey.INVALID_DATA, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> prepareResponse(Map<Object, Object> data, String key, HttpStatus status) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("message", key);
        result.put("timestamp", getTime());
        return new ResponseEntity<>(result, status);
    }

    private ResponseEntity<Object> prepareResponse(Object data, String key) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("message", key);
        result.put("timestamp", getTime());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private ResponseEntity<Object> prepareResponse(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("message", MessageKey.SUCCESS);
        result.put("timestamp", getTime());
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    private ResponseEntity<Object> prepareResponse(String data, String key) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", key.formatted(data));
        result.put("timestamp", getTime());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ResponseData<String>> prepareResponse(String key) {
        return prepareResponse(key, HttpStatus.BAD_REQUEST);
    }

}
