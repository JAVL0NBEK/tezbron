package com.example.bron.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {

  public BadRequestException(String code, List<?> details) {
    super(HttpStatus.BAD_REQUEST, code, details);
  }

  public BadRequestException(String code) {
    super(HttpStatus.BAD_REQUEST, code, List.of());
  }
}
