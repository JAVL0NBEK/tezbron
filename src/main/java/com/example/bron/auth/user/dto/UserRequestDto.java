package com.example.bron.auth.user.dto;

import com.example.bron.enums.Role;
import com.example.bron.stadium.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
  private String username;
  private String passwordHash;
  private Role role;
  private String phone;
  private LocationDto location;
}
