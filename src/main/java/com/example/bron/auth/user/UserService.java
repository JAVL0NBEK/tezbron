package com.example.bron.auth.user;

import com.example.bron.auth.user.dto.AssignRoleRequestDto;
import com.example.bron.auth.user.dto.CreateStaffUserDto;
import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.auth.user.dto.UserRequestDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import java.util.List;

public interface UserService {
  UserDTO create(UserRequestDto dto);
  UserDTO createStaff(CreateStaffUserDto dto);
  String generateStaffPassword();
  UserDTO update(Long id, UserRequestDto dto);
  void delete(Long id);
  UserDTO getById(Long id);
  List<UserDTO> getAll();
  void assignRoles(Long userId, AssignRoleRequestDto dto);
  void removeRole(Long userId, Long roleId);
  List<RoleResponseDto> getRoles(Long userId);
}
