package com.example.blood_donation.exception;


import com.example.blood_donation.dto.responses.ErrorResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@FieldDefaults(level = AccessLevel.PUBLIC)
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = Exception.class)
//    ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException exception){
//        log.error("Exception: ", exception);
//        ApiResponse<?> apiResponse = new ApiResponse<>();
//
//        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
//
//
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ErrorResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ErrorResponse.builder()
                        .code(errorCode.getCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        String message = exception.getMessage();
        String userMessage = getString(message);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .code(ErrorCode.INVALID_DATA.getCode())
                        .message(userMessage)
                        .build());
    }

    private String getString(String message) {
        String userMessage = "Invalid data provided";

        // Check for duplicate entry error
        if (message != null && message.contains("Duplicate entry")) {
            // Extract the duplicate value and field name
            if (message.contains("phone")) {
                userMessage = "Phone number already exists";
            } else if (message.contains("email")) {
                userMessage = "Email address already exists";
            } else {
                userMessage = "This value already exists";
            }
        }
        return userMessage;
    }

    //    @ExceptionHandler(value = AccessDeniedException.class)
//    ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception){
//        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
//
//        return ResponseEntity.status(errorCode.getStatusCode()).body(
//                ApiResponse.builder()
//                        .code(errorCode.getCode())
//                        .build()
//        );
//    }
//
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handlingValidation(MethodArgumentNotValidException exception) {
        Object[] arrayMsgErrors = exception.getDetailMessageArguments();
        String msg = "";
        if (arrayMsgErrors.length > 0) {
            msg = arrayMsgErrors[0].toString();
            System.err.println("INSIDE GLOBAL EXCEPTION HANDLER");
        }
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .code(exception.getStatusCode().value())
                        .message(msg)
                        .build()
        );
    }
}

