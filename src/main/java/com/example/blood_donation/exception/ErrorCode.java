package com.example.blood_donation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, HttpStatus.BAD_REQUEST),
    RESOURCED_NOT_FOUND(1009, HttpStatus.BAD_REQUEST),
    DUPLICATE_RESOURCE(1010, HttpStatus.BAD_REQUEST);

    private final int code;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, HttpStatusCode statusCode) {
        this.code = code;
        this.statusCode = statusCode;
    }


}
