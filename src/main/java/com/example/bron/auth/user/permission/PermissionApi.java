package com.example.bron.auth.user.permission;

import com.example.bron.auth.user.permission.dto.PermissionRequestDto;
import com.example.bron.auth.user.permission.dto.PermissionResponseDto;
import com.example.bron.common.BaseResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/admin/permissions")
public interface PermissionApi {

  @PostMapping("/create")
  ResponseEntity<BaseResponse<PermissionResponseDto>> create(@RequestBody @Valid PermissionRequestDto dto);

  @GetMapping
  ResponseEntity<BaseResponse<List<PermissionResponseDto>>> getPermissions();

  @DeleteMapping("/{id}")
  ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id);

}
