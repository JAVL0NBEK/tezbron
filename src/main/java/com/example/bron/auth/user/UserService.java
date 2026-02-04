package com.example.bron.auth.user;

import com.example.bron.auth.user.dto.AssignRoleRequestDto;
import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.auth.user.dto.UserRequestDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
  UserDTO create(UserRequestDto dto);
  UserDTO update(Long id, UserRequestDto dto);
  void delete(Long id);
  UserDTO getById(Long id);
  List<UserDTO> getAll();
  void assignRoles(Long userId, AssignRoleRequestDto dto);
  void removeRole(Long userId, Long roleId);
  List<RoleResponseDto> getRoles(Long userId);
}
