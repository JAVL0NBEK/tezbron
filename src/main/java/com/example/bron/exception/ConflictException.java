package com.example.bron.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException {

  public ConflictException(String code, List<?> details) {
    super(HttpStatus.CONFLICT, code, details);
  }

  public ConflictException(String code) {
    super(HttpStatus.CONFLICT, code, List.of());
  }
}
