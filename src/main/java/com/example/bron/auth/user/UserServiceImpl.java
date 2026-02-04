package com.example.bron.auth.user;

import com.example.bron.auth.user.dto.AssignRoleRequestDto;
import com.example.bron.auth.user.role.RoleEntity;
import com.example.bron.auth.user.role.RoleMapper;
import com.example.bron.auth.user.role.RoleRepository;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import com.example.bron.exception.NotFoundException;
import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.auth.user.dto.UserRequestDto;
import com.example.bron.location.DistrictRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final DistrictRepository districtRepository;
  private final UserMapper mapper;
  private final RoleMapper  roleMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDTO create(UserRequestDto dto) {
    var user = mapper.toEntity(dto);
    user.setCreatedAt(LocalDateTime.now());
    user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
    var playerRole = roleRepository.findByName("ROLE_PLAYER")
        .orElseThrow(() -> new NotFoundException("Default role not found", List.of("ROLE_PLAYER")));
    user.getRoles().add(playerRole);
    var saved = userRepository.save(user);
    return mapper.toDto(saved);
  }

  @Override
  public UserDTO update(Long id, UserRequestDto dto) {
    var user = getFindById(id);
    mapper.updateEntity(user, dto);
    var saved = userRepository.save(user);
    return mapper.toDto(saved);
  }

  @Override
  public void delete(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public UserDTO getById(Long id) {
    var user = getFindById(id);
    return mapper.toDto(user);
  }

  @Override
  public List<UserDTO> getAll() {
    var users = userRepository.findAll();
    return users.stream()
        .map(mapper::toDto)
        .toList();
  }

  private UserEntity getFindById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("user_not_found",List.of(id.toString())));
  }

  @Transactional
  @Override
  public void assignRoles(Long userId, AssignRoleRequestDto roleDto) {
    var user = getFindById(userId);

    Set<RoleEntity> roles = new HashSet<>(roleRepository.findAllById(roleDto.getRoleIds()));

    if (roles.size() != roleDto.getRoleIds().size()) {
      throw new NotFoundException("one_or_more_roles_not_found",List.of(roleDto.getRoleIds().toString()));
    }

    user.getRoles().addAll(roles);
  }

  @Transactional
  @Override
  public void removeRole(Long userId, Long roleId) {
    var user = getFindById(userId);

    var role = roleRepository.findById(roleId)
        .orElseThrow(() -> new RuntimeException("Role not found"));

    user.getRoles().remove(role);
  }

  @Override
  public List<RoleResponseDto> getRoles(Long userId) {
    var user = getFindById(userId);
    var roles = user.getRoles();
    return roles.stream().map(roleMapper::toDto).toList();
  }

}
