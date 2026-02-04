package com.example.bron.auth.user.dto;

import com.example.bron.enums.Role;
import com.example.bron.stadium.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
  private String username;
  private String password; // so‘ngra BCrypt bilan hash qilamiz
  private String phone;
  private String fullName;
  private String profileImageUrl;
  private String location; // json string sifatida
  private Long districtId;
}
