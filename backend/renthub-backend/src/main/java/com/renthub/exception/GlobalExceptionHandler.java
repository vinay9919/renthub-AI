package com.renthub.exception;

import com.renthub.booking.exception.BookingNotFoundException;
import com.renthub.common.response.ErrorResponse;
import com.renthub.common.response.ValidationErrorResponse;
import com.renthub.listing.exception.ListingNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
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
    @ExceptionHandler(ListingNotFoundException.class)
public ResponseEntity<ErrorResponse> handleListingNotFound(
        ListingNotFoundException ex) {

    ErrorResponse response = new ErrorResponse(
            false,
            ex.getMessage(),
            404,
            LocalDateTime.now()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response);
}
    
@ExceptionHandler(AccessDeniedException.class)
public ResponseEntity<ErrorResponse> handleAccessDenied(
        AccessDeniedException ex) {

    ErrorResponse response = new ErrorResponse(
            false,
            ex.getMessage(),
            HttpStatus.FORBIDDEN.value(),
            LocalDateTime.now()
    );

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(response);
}
@ExceptionHandler(BookingNotFoundException.class)
public ResponseEntity<ErrorResponse> handleBookingNotFound(
        BookingNotFoundException ex) {

    ErrorResponse response = new ErrorResponse(
            false,
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response);
}
@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<ErrorResponse> handleIllegalArgument(
        IllegalArgumentException ex) {

    return ResponseEntity.badRequest().body(
            ErrorResponse.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build()
    );
}
}