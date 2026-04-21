package com.example.bron.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApiException {

  public ForbiddenException(String code, List<?> details) {
    super(HttpStatus.FORBIDDEN, code, details);
  }

  public ForbiddenException(String code) {
    super(HttpStatus.FORBIDDEN, code, List.of());
  }
}
