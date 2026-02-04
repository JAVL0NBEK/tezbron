package com.example.bron.auth.user.role;

import com.example.bron.auth.user.role.dto.RoleRequestDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import java.util.List;

public interface RoleService {
  RoleResponseDto createRole(RoleRequestDto dto);

  List<RoleResponseDto> getRoles();

  RoleResponseDto addPermission(Long roleId, Long permissionId);

  RoleResponseDto removePermission(Long roleId, Long permissionId);

}
