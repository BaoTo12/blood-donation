package com.example.blood_donation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1003, HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, HttpStatus.FORBIDDEN),
    RESOURCED_NOT_FOUND(1009, HttpStatus.BAD_REQUEST),
    DUPLICATE_RESOURCE(1010, HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1011, HttpStatus.BAD_REQUEST),
    INVALID_DATA(1012, HttpStatus.BAD_REQUEST),
    FIELD_NOT_EXIST(1013, HttpStatus.BAD_REQUEST),
    FAILED_TO_SEND_EMAIL(1014, HttpStatus.SERVICE_UNAVAILABLE);

    private final int code;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, HttpStatusCode statusCode) {
        this.code = code;
        this.statusCode = statusCode;
    }


}
