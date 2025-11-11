package com.example.bron.auth.user.dto;

import com.example.bron.enums.Role;
import com.example.bron.stadium.dto.LocationDto;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.Type;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private Long id;
  private String username;
  private String passwordHash;
  private Role role;
  private String phone;
  private String fullName;
  private String profileImageUrl;
  private LocationDto location;
  private LocalDateTime createdAt;
}
