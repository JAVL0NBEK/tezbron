package com.example.bron.auth.security.refresh;

import com.example.bron.exception.UnauthorizedException;

public class RefreshTokenException extends UnauthorizedException {
  public RefreshTokenException(String code) {
    super(code);
  }
}
