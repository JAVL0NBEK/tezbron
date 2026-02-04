package com.example.bron.auth.user;

import com.example.bron.auth.user.dto.AssignRoleRequestDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import com.example.bron.common.BaseResponse;
import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.auth.user.dto.UserRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/users")
@Tag(name = "Users Management APIs", description = "Endpoints for managing users")
public interface UserApi {
  @PostMapping("/create")
  ResponseEntity<BaseResponse<UserDTO>> create(@RequestBody UserRequestDto dto);

  @PutMapping("/{id}")
  ResponseEntity<BaseResponse<UserDTO>> update(@PathVariable Long id, @RequestBody UserRequestDto dto);

  @GetMapping("/{id}")
  ResponseEntity<BaseResponse<UserDTO>> get(@PathVariable Long id);

  @GetMapping()
  ResponseEntity<BaseResponse<List<UserDTO>>> getAll();

  @DeleteMapping("/{id}")
  ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id);

  @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('DISTRICT_ADMIN')")
  @PostMapping("/{userId}/roles")
  ResponseEntity<BaseResponse<Void>> assignRoles(@PathVariable Long userId,
      @RequestBody @Valid AssignRoleRequestDto dto);

  @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('DISTRICT_ADMIN')")
  @DeleteMapping("/{userId}/roles/{roleId}")
  ResponseEntity<BaseResponse<Void>> removeRole(@PathVariable Long userId,
      @PathVariable Long roleId);

  @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('DISTRICT_ADMIN')")
  @GetMapping("/{userId}/roles")
  ResponseEntity<BaseResponse<List<RoleResponseDto>>> getRoles(@PathVariable Long userId);

}
