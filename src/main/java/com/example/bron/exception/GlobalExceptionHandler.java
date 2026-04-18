package com.example.bron.exception;

import com.example.bron.common.BaseResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<BaseResponse<Void>> handleApi(ApiException ex) {
    log.warn("API exception: status={}, code={}, details={}", ex.getStatus(), ex.getCode(), ex.getDetails());
    return ResponseEntity
        .status(ex.getStatus())
        .body(BaseResponse.error(ex.getCode(), ex.getCode(), ex.getDetails()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
    List<Object> details = new ArrayList<>();
    ex.getBindingResult().getFieldErrors().forEach(fe ->
        details.add(fe.getField() + ": " + fe.getDefaultMessage()));
    log.warn("Validation failed: {}", details);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(BaseResponse.error("VALIDATION_ERROR", "Validation failed", details));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<BaseResponse<Void>> handleConstraint(ConstraintViolationException ex) {
    List<Object> details = new ArrayList<>();
    ex.getConstraintViolations().forEach(v ->
        details.add(v.getPropertyPath() + ": " + v.getMessage()));
    log.warn("Constraint violation: {}", details);
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(BaseResponse.error("VALIDATION_ERROR", "Validation failed", details));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<BaseResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
    String code = ex.getMessage() == null || ex.getMessage().isBlank() ? "FORBIDDEN" : ex.getMessage();
    log.warn("Access denied: {}", code);
    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(BaseResponse.error(code, code, null));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<BaseResponse<Void>> handleAuthentication(AuthenticationException ex) {
    log.warn("Authentication failed: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(BaseResponse.error("UNAUTHENTICATED", "UNAUTHENTICATED", null));
  }

  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<BaseResponse<Void>> handleExpiredJwt(ExpiredJwtException ex) {
    log.warn("Token expired: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(BaseResponse.error("TOKEN_EXPIRED", "TOKEN_EXPIRED", null));
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<BaseResponse<Void>> handleJwt(JwtException ex) {
    log.warn("Invalid token: {}", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(BaseResponse.error("TOKEN_INVALID", "TOKEN_INVALID", null));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<BaseResponse<Void>> handleDataIntegrity(DataIntegrityViolationException ex) {
    log.warn("Data integrity violation: {}", ex.getMostSpecificCause().getMessage());
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(BaseResponse.error("DATA_INTEGRITY_VIOLATION", "DATA_INTEGRITY_VIOLATION", null));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse<Void>> handleAll(Exception ex) {
    log.error("Unhandled exception", ex);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(BaseResponse.error("INTERNAL_ERROR", "Internal server error", null));
  }
}
