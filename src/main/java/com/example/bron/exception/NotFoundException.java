package com.example.bron.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

  public NotFoundException(String code, List<?> details) {
    super(HttpStatus.NOT_FOUND, code, details);
  }

  public NotFoundException(String code) {
    super(HttpStatus.NOT_FOUND, code, List.of());
  }
}
