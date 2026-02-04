package com.example.bron.auth.user.role;

import com.example.bron.auth.user.role.dto.RoleRequestDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import com.example.bron.common.BaseResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/admin/roles")
public interface RoleApi {

  @PostMapping
  ResponseEntity<BaseResponse<RoleResponseDto>> createRole(@Valid @RequestBody RoleRequestDto dto);

  @GetMapping
  ResponseEntity<BaseResponse<List<RoleResponseDto>>> getRoles();

  @PostMapping("/{roleId}/permissions/{permissionId}")
  ResponseEntity<BaseResponse<RoleResponseDto>> addPermission(@PathVariable("roleId") Long roleId, @PathVariable("permissionId") Long permissionId);

  @DeleteMapping("/{roleId}/permissions/{permissionId}")
  ResponseEntity<BaseResponse<RoleResponseDto>> removePermission(@PathVariable Long roleId,
      @PathVariable Long permissionId);

}
