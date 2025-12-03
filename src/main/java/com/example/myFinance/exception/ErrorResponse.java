package com.example.myFinance.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;              // HTTP status code
    private String error;            // HTTP status reason
    private String message;          // Detailed message
    private String path;             // Request path
    private List<String> details;    // Optional list of extra details (e.g. validation errors)
}
