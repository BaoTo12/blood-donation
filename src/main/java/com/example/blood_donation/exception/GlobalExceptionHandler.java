package com.example.blood_donation.exception;


import com.example.blood_donation.dto.response.ErrorResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
        System.err.println("HANDLE APP EXCEPTION: " + exception.getMessage());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ErrorResponse.builder()
                        .code(errorCode.getCode())
                        .message(exception.getMessage())
                        .build());
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
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException exception){
//        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
//
//        ErrorCode errorCode = ErrorCode.INVALID_KEY;
//
//        try {
//            errorCode = ErrorCode.valueOf(enumKey);
//        } catch (IllegalArgumentException e){
//            System.err.println(e.getMessage());
//        }
//
//        ApiResponse<?> apiResponse = new ApiResponse<>();
//
//        apiResponse.setCode(errorCode.getCode());
//        apiResponse.setMessage(errorCode.getMessage());
//
//        return ResponseEntity.badRequest().body(apiResponse);
//    }
}

