package com.example.bron.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

  public UnauthorizedException(String code, List<?> details) {
    super(HttpStatus.UNAUTHORIZED, code, details);
  }

  public UnauthorizedException(String code) {
    super(HttpStatus.UNAUTHORIZED, code, List.of());
  }
}
