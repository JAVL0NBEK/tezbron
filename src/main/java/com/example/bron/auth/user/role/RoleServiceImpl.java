package com.example.bron.auth.user.role;

import com.example.bron.auth.user.UserRepository;
import com.example.bron.auth.user.permission.PermissionRepository;
import com.example.bron.auth.user.role.dto.RoleRequestDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import com.example.bron.exception.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final PermissionRepository permissionRepository;
  private final RoleMapper roleMapper;

  @Override
  public RoleResponseDto createRole(RoleRequestDto dto) {
    if (roleRepository.existsByName(dto.getName())) {
      throw new NotFoundException("Role already exists", List.of(dto.getName()));
    }
    return roleMapper.toDto(roleRepository.save(roleMapper.toEntity(dto)));
  }

  @Override
  public List<RoleResponseDto> getRoles() {
    var roles = roleRepository.findAll();
    return roles.stream()
        .map(roleMapper::toDto)
        .toList();
  }

  @Override
  public RoleResponseDto addPermission(Long roleId, Long permissionId) {
    var role = roleRepository.findById(roleId).orElseThrow();
    var permission = permissionRepository.findById(permissionId).orElseThrow();
    role.addPermission(permission);
    return roleMapper.toDto(role);
  }

  @Override
  public RoleResponseDto removePermission(Long roleId, Long permissionId) {
    var role = roleRepository.findById(roleId).orElseThrow();
    var permission = permissionRepository.findById(permissionId).orElseThrow();
    role.removePermission(permission);
    return roleMapper.toDto(role);
  }
}
