package com.example.bron.auth.user;

import com.example.bron.auth.otp.EskizSmsService;
import com.example.bron.auth.security.CurrentUserService;
import com.example.bron.auth.user.dto.AssignRoleRequestDto;
import com.example.bron.auth.user.dto.CreateStaffUserDto;
import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.auth.user.dto.UserRequestDto;
import com.example.bron.auth.user.role.RoleEntity;
import com.example.bron.auth.user.role.RoleMapper;
import com.example.bron.auth.user.role.RoleRepository;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import com.example.bron.coach.CoachEntity;
import com.example.bron.coach.CoachRepository;
import com.example.bron.enums.StaffRole;
import com.example.bron.exception.ConflictException;
import com.example.bron.exception.ForbiddenException;
import com.example.bron.exception.NotFoundException;
import com.example.bron.location.DistrictEntity;
import com.example.bron.location.DistrictRepository;
import com.example.bron.stadium.StadiumRepository;
import jakarta.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private static final String PASSWORD_ALPHABET =
      "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789";
  private static final int GENERATED_PASSWORD_LENGTH = 8;
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final DistrictRepository districtRepository;
  private final StadiumRepository stadiumRepository;
  private final CoachRepository coachRepository;
  private final EskizSmsService eskizSmsService;
  private final UserMapper mapper;
  private final RoleMapper roleMapper;
  private final PasswordEncoder passwordEncoder;
  private final CurrentUserService currentUserService;

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

  @Transactional
  @Override
  public UserDTO createStaff(CreateStaffUserDto dto) {
    if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
      throw new ConflictException("USERNAME_ALREADY_EXISTS", List.of(dto.getUsername()));
    }

    DistrictEntity targetDistrict = resolveTargetDistrictForStaff(dto);

    RoleEntity role = roleRepository.findByName(dto.getRole().getRoleName())
        .orElseThrow(() -> new NotFoundException("role_not_found",
            List.of(dto.getRole().getRoleName())));

    UserEntity user = new UserEntity();
    user.setUsername(dto.getUsername());
    user.setFullName(dto.getFullName());
    user.setPhone(dto.getPhone());
    user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
    user.setCreatedAt(LocalDateTime.now());
    user.getRoles().add(role);
    user.setDistrict(targetDistrict);

    UserEntity saved = userRepository.save(user);

    if (dto.getRole() == StaffRole.COACH) {
      CoachEntity coach = new CoachEntity();
      coach.setUser(saved);
      coachRepository.save(coach);
    }

//    String message = String.format(
//        "Tezbron tizimiga kirish. Login: %s, Parol: %s",
//        dto.getUsername(), rawPassword);
//    eskizSmsService.sendSms(dto.getPhone(), message);

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
      throw new NotFoundException("ROLE_NOT_FOUND", List.of(roleDto.getRoleIds().toString()));
    }

    user.getRoles().addAll(roles);
  }

  @Transactional
  @Override
  public void removeRole(Long userId, Long roleId) {
    var user = getFindById(userId);

    var role = roleRepository.findById(roleId)
        .orElseThrow(() -> new NotFoundException("ROLE_NOT_FOUND", List.of(roleId.toString())));

    user.getRoles().remove(role);
  }

  @Override
  public List<RoleResponseDto> getRoles(Long userId) {
    var user = getFindById(userId);
    var roles = user.getRoles();
    return roles.stream().map(roleMapper::toDto).toList();
  }

  private DistrictEntity resolveTargetDistrictForStaff(CreateStaffUserDto dto) {
    StaffRole requestedRole = dto.getRole();

    if (currentUserService.isSuperAdmin()) {
      if (dto.getDistrictId() == null) {
        return null;
      }
      return districtRepository.findById(dto.getDistrictId())
          .orElseThrow(() -> new NotFoundException("district_not_found",
              List.of(dto.getDistrictId().toString())));
    }

    if (currentUserService.isDistrictAdmin()) {
      if (requestedRole == StaffRole.SUPER_ADMIN || requestedRole == StaffRole.DISTRICT_ADMIN) {
        throw new ForbiddenException("DISTRICT_ADMIN_CANNOT_CREATE_ADMIN");
      }
      DistrictEntity own = currentUserService.getCurrentUser().getDistrict();
      if (own == null) {
        throw new ForbiddenException("CURRENT_ADMIN_HAS_NO_DISTRICT");
      }
      return own;
    }

    throw new ForbiddenException("INSUFFICIENT_ROLE_TO_CREATE_STAFF");
  }

  @Override
  public String generateStaffPassword() {
    StringBuilder sb = new StringBuilder(GENERATED_PASSWORD_LENGTH);
    for (int i = 0; i < GENERATED_PASSWORD_LENGTH; i++) {
      sb.append(PASSWORD_ALPHABET.charAt(SECURE_RANDOM.nextInt(PASSWORD_ALPHABET.length())));
    }
    return sb.toString();
  }
}
