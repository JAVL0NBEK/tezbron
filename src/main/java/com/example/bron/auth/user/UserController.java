package com.example.bron.auth.user;

import com.example.bron.auth.user.dto.AssignRoleRequestDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import com.example.bron.common.BaseResponse;
import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.auth.user.dto.UserRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
  private final UserService service;
  private final UserService userService;

  @Override
  public ResponseEntity<BaseResponse<UserDTO>> create(UserRequestDto dto) {
    var user = service.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.ok(user));
  }

  @Override
  public ResponseEntity<BaseResponse<UserDTO>> update(Long id, UserRequestDto dto) {
    var user = service.update(id, dto);
    return ResponseEntity.ok(BaseResponse.ok(user));
  }

  @Override
  public ResponseEntity<BaseResponse<UserDTO>> get(Long id) {
    var user = userService.getById(id);
    return ResponseEntity.ok(BaseResponse.ok(user));
  }

  @Override
  public ResponseEntity<BaseResponse<List<UserDTO>>> getAll() {
    var users = userService.getAll();
    return ResponseEntity.ok(BaseResponse.ok(users));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> delete(Long id) {
    userService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(BaseResponse.noContent("User deleted successfully"));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> assignRoles(Long userId, AssignRoleRequestDto dto) {
    userService.assignRoles(userId, dto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(BaseResponse.noContent("User assign roles successfully"));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> removeRole(Long userId, Long roleId) {
    userService.removeRole(userId, roleId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(BaseResponse.noContent("User remove roles successfully"));
  }

  @Override
  public ResponseEntity<BaseResponse<List<RoleResponseDto>>> getRoles(Long userId) {
    var  userRoles = userService.getRoles(userId);
    return ResponseEntity.ok(BaseResponse.ok(userRoles));
  }
}
