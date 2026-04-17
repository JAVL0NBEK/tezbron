package com.example.bron.auth.dto;

import com.example.bron.enums.LoginStatus;
import com.example.bron.location.DistrictEntity;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
  private Long id;

  private String username;

  private String passwordHash;

  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpiresIn;  // sekundda
  private Long refreshTokenExpiresIn; // sekundda

  private DistrictEntity district;

  private String phone;
  private String fullName;
  private String profileImageUrl;
  private LocalDateTime createdAt;
  private LoginStatus status;

  private Set<String> roles;

  public LoginResponseDto(LoginStatus status) {
    this.status = status;
  }
}
