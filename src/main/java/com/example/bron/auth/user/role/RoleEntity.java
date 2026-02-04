package com.example.bron.auth.user.role;

import com.example.bron.auth.user.permission.PermissionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Masalan: ROLE_SUPER_ADMIN
  @Column(unique = true, nullable = false)
  private String name;

  // 🔹 Har bir role bir nechta permissionga ega
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "role_permissions",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id")
  )
  private Set<PermissionEntity> permissions = new HashSet<>();

  public void addPermission(PermissionEntity permission) {
    this.permissions.add(permission);
    permission.getRoles().add(this);
  }

  public void removePermission(PermissionEntity permission) {
    this.permissions.remove(permission);
    permission.getRoles().remove(this);
  }
}
