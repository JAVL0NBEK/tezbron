package com.example.bron.auth.user.permission;

import com.example.bron.auth.user.role.RoleEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Masalan: STADIUM_CREATE
  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany(mappedBy = "permissions")
  private Set<RoleEntity> roles = new HashSet<>();

  public PermissionEntity(String name) {
    this.name = name;
  }
}
