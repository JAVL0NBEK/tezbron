package com.example.bron.exception;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApiException extends RuntimeException {

  private final HttpStatus status;
  private final String code;
  private final List<Object> details;

  protected ApiException(HttpStatus status, String code, List<?> details) {
    super(code);
    this.status = status;
    this.code = code;
    this.details = details == null ? Collections.emptyList() : List.copyOf(details);
  }
}
