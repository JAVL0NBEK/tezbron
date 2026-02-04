package com.example.bron.auth.user.permission;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
  Optional<PermissionEntity> findByName(String name);

  boolean existsByName(String name);

}
