package com.example.bron.auth.user;

import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.auth.user.dto.UserRequestDto;
import java.util.List;

public interface UserService {
  UserDTO create(UserRequestDto dto);
  UserDTO update(Long id, UserRequestDto dto);
  void delete(Long id);
  UserDTO getById(Long id);
  List<UserDTO> getAll();
}
