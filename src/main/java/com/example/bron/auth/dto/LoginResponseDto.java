package com.example.bron.auth.dto;

import com.example.bron.auth.user.role.RoleEntity;
import com.example.bron.location.DistrictEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

@Data
@AllArgsConstructor
public class LoginResponseDto {
  private Long id;

  private String username;

  private String passwordHash;

  private String accessToken;

  private DistrictEntity district;

  private String phone;
  private String fullName;
  private String profileImageUrl;
  private LocalDateTime createdAt;
}
