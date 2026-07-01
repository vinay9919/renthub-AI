package com.renthub.exception;

import com.renthub.common.response.ErrorResponse;
import com.renthub.common.response.ValidationErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ValidationErrorResponse> handleValidation(
        MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));

    ValidationErrorResponse response =
            ValidationErrorResponse.builder()
                    .success(false)
                    .message("Validation Failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(errors)
                    .build();

    return ResponseEntity.badRequest().body(response);
}

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExists(
            EmailAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .status(HttpStatus.CONFLICT.value())
                                .build()
                );
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePhoneExists(
            PhoneAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .status(HttpStatus.CONFLICT.value())
                                .build()
                );
    }

}