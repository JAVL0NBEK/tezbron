package com.example.bron.auth.user.role.dto;

import com.example.bron.auth.user.permission.dto.PermissionResponseDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleResponseDto {
  private Long id;

  private String name;

  public RoleResponseDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  private List<PermissionResponseDto> permissions;
}
