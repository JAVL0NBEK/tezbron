package com.example.bron.admin;

import com.example.bron.auth.user.UserService;
import com.example.bron.auth.user.dto.CreateStaffUserDto;
import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminUserController implements AdminUserApi {

  private final UserService userService;

  @Override
  public ResponseEntity<BaseResponse<UserDTO>> createStaff(CreateStaffUserDto dto) {
    UserDTO created = userService.createStaff(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.ok(created));
  }

  @Override
  public ResponseEntity<BaseResponse<String>> generatePassword() {
    return ResponseEntity.ok(BaseResponse.ok(userService.generateStaffPassword()));
  }
}
