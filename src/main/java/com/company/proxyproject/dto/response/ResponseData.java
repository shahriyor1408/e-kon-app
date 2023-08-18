package com.company.proxyproject.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
    @JsonProperty("data")
    private T data;
    @JsonProperty("message")
    private String message;
    @JsonProperty("timestamp")
    private String timestamp;

    public ResponseData(T data) {
        this.data = data;
        this.message = "";
        this.timestamp = getTime();
    }

    public ResponseData(String message) {
        this.message = "";
        this.data = (T) message;
        this.timestamp = getTime();
    }

    public ResponseData(T data, String message) {
        this.data = data;
        this.message = message;
        this.timestamp = getTime();
    }


    public ResponseData() {
        this.message = "";
        this.timestamp = getTime();
    }

    public static <T> ResponseEntity<ResponseData<T>> response(T data) {
        return ResponseEntity.ok(new ResponseData<>(data));
    }

    public static <T> ResponseEntity<ResponseData<T>> response(ResponseData<T> responseData, HttpStatus status) {
        return new ResponseEntity<>(responseData, status);
    }

    public static <T> ResponseEntity<ResponseData<T>> response(String errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseData<>(null, errorMessage), httpStatus);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }


    @JsonIgnore
    public String getTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return now.format(format);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
