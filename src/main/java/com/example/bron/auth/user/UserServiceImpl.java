package com.example.bron.auth.user;

import com.example.bron.exception.NotFoundException;
import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.auth.user.dto.UserRequestDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper mapper;

  @Override
  public UserDTO create(UserRequestDto dto) {
    var user = mapper.toEntity(dto);
    user.setCreatedAt(LocalDateTime.now());
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
}
