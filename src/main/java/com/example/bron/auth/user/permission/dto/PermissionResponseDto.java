package com.example.bron.auth.user.permission.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PermissionResponseDto {
  private Long id;
  private String name;

}
