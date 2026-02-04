package com.example.bron.auth.user.permission;

import com.example.bron.auth.user.permission.dto.PermissionRequestDto;
import com.example.bron.auth.user.permission.dto.PermissionResponseDto;
import java.util.List;

public interface PermissionService {

  PermissionResponseDto create(PermissionRequestDto dto);

  List<PermissionResponseDto> getPermissions();

  void delete(Long id);

}
