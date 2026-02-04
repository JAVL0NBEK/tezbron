package com.example.bron.auth.user.permission;

import com.example.bron.auth.user.permission.dto.PermissionRequestDto;
import com.example.bron.auth.user.permission.dto.PermissionResponseDto;
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
public class PermissionController implements PermissionApi {
  private final PermissionService permissionService;

  @Override
  public ResponseEntity<BaseResponse<PermissionResponseDto>> create(PermissionRequestDto dto) {
    var response = permissionService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.ok(response));
  }

  @Override
  public ResponseEntity<BaseResponse<List<PermissionResponseDto>>> getPermissions() {
    return ResponseEntity.ok(BaseResponse.ok(permissionService.getPermissions()));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> delete(Long id) {
    permissionService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(BaseResponse.noContent("Permission deleted successfully"));
  }
}
