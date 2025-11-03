package com.example.bron.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 🟢 Custom exception (masalan, ResourceNotFoundException)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 🟠 Illegal argument / validation xatolari
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 🔴 Barcha qolgan xatolar uchun (general)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneral(Exception ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "internal_server_error";

        // ⚠️ Swagger API (springdoc-openapi) xatolarini tutmaslik
        if (isSwaggerError(ex)) {
            // Swagger uchun hech narsa qaytarmaymiz – u o‘zi handle qiladi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        ex.printStackTrace(); // log uchun
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message) {
        ApiErrorResponse response = new ApiErrorResponse(status.value(), message);
        return ResponseEntity.status(status).body(response);
    }

    // Swagger uchun istisno qilinadigan joylar
    private boolean isSwaggerError(Exception ex) {
        String trace = ex.toString().toLowerCase();
        return trace.contains("springdoc")
                || trace.contains("swagger")
                || trace.contains("openapi");
    }

    // JSON javob formati
    public record ApiErrorResponse(int status, String message) {}
}
