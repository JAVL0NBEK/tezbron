package com.example.bron.auth.user.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleRequestDto {
  @NotBlank
  private String name;
}
