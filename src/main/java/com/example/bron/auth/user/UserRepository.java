package com.example.bron.auth.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  @Query("""
    select u from UserEntity u
    left join fetch u.roles r
    left join fetch r.permissions
    where u.username = :username
""")
  Optional<UserEntity> findByUsernameWithRolesAndPermissions(String username);
  Optional<UserEntity> findByPhone(String phone);

  Optional<UserEntity> findByUsername(String username);
}
