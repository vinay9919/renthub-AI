package com.renthub.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private boolean success;

    private String message;

    private int status;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}