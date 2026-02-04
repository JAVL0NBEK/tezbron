package com.example.bron.auth.user.dto;

import com.example.bron.auth.user.permission.dto.PermissionResponseDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import com.example.bron.enums.Role;
import com.example.bron.stadium.dto.LocationDto;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Set;
import lombok.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.Type;

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
