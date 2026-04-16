package com.example.bron.auth.user.dto;

import com.example.bron.auth.user.permission.dto.PermissionResponseDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import java.util.Set;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private Long id;
  private String username;
  private String phone;
  private String fullName;
  private String profileImageUrl;
  private String location;
  private Long districtId;
  private String districtName;
  private Set<RoleResponseDto> roles;
  private Set<PermissionResponseDto> permissions;
  private LocalDateTime createdAt;
}
