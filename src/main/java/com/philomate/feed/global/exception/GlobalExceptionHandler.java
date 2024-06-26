package com.philomate.feed.global.exception;

import com.philomate.feed.global.exception.customException.NotEnoughAuthorityException;
import com.philomate.feed.global.exception.customException.NotFoundException;
import com.philomate.feed.global.response.ApiResponse;
import com.philomate.feed.global.response.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Object>> unmatchedExceptionHandler(Exception e) {
        log.error("unmatched exception occurred", e);
        final ApiResponse<Object> response = ApiResponse.failure(ResultCode.BAD_REQUEST,
            e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> runtimeExceptionHandler(Exception e) {
        log.error("runtime exception occurred", e);
        final ApiResponse<Object> response = ApiResponse.failure(ResultCode.INTERNAL_SERVER_ERROR,
            e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NotEnoughAuthorityException.class)
    public ResponseEntity<ApiResponse<Object>> notEnoughAuthorityExceptionHandler(Exception e) {
        final ApiResponse<Object> response = ApiResponse.failure(ResultCode.FORBIDDEN,
            e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> illegalArgumentExceptionHandler(Exception e) {
        final ApiResponse<Object> response = ApiResponse.failure(ResultCode.BAD_REQUEST,
            e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<ApiResponse<Object>> illegalStateExceptionHandler(Exception e) {
        final ApiResponse<Object> response = ApiResponse.failure(ResultCode.BAD_REQUEST,
            e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> notFoundExceptionHandler(Exception e) {
        final ApiResponse<Object> response = ApiResponse.failure(ResultCode.NOT_FOUND,
            e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
