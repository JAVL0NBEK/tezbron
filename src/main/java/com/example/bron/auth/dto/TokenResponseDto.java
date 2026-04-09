package com.example.bron.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDto {
  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpiresIn;
  private Long refreshTokenExpiresIn;
}
