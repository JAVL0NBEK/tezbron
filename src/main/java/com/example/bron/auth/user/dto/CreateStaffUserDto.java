package com.example.bron.auth.user.dto;

import com.example.bron.enums.StaffRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateStaffUserDto {
  @NotBlank
  private String fullName;

  @NotBlank
  private String username;

  @NotBlank
  private String phone;

  @NotBlank
  private String password;

  @NotNull
  private StaffRole role;

  private Long districtId;
}
