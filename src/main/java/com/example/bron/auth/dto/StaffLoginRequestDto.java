package com.example.bron.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StaffLoginRequestDto {
  @NotBlank
  private String username;

  @NotBlank
  private String password;
}
