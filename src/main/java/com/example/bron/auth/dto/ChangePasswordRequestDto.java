package com.example.bron.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequestDto {
  @NotBlank
  private String oldPassword;

  @NotBlank
  @Size(min = 8, message = "Parol kamida 6 ta belgidan iborat bo'lishi kerak")
  private String newPassword;
}
