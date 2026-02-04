package com.example.bron.auth.user.role;

import com.example.bron.auth.user.role.dto.RoleRequestDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import com.example.bron.common.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoleController implements RoleApi {
  private final RoleService roleService;

  @Override
  public ResponseEntity<BaseResponse<RoleResponseDto>> createRole(RoleRequestDto dto) {
    var role = roleService.createRole(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.ok(role));
  }

  @Override
  public ResponseEntity<BaseResponse<List<RoleResponseDto>>> getRoles() {
    var roles = roleService.getRoles();
    return ResponseEntity.ok(BaseResponse.ok(roles));
  }

  @Override
  public ResponseEntity<BaseResponse<RoleResponseDto>> addPermission(Long roleId,
      Long permissionId) {
    var role = roleService.addPermission(roleId, permissionId);
    return ResponseEntity.ok(BaseResponse.ok(role));
  }

  @Override
  public ResponseEntity<BaseResponse<RoleResponseDto>> removePermission(Long roleId, Long permissionId) {
    roleService.removePermission(roleId, permissionId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(BaseResponse.noContent("Role deleted successfully"));
  }
}
