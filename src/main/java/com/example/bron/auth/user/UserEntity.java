package com.example.bron.auth.user;

import com.example.bron.auth.user.role.RoleEntity;
import com.example.bron.booking.BookingEntity;
import com.example.bron.location.DistrictEntity;
import com.example.bron.team.TeamEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private DistrictEntity district;

    // Userda bir nechta role bo‘lishi mumkin
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<RoleEntity> roles = new HashSet<>();

    @Column(unique = true, nullable = false)
    private String phone;
    private String fullName;
    private String profileImageUrl;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private String location;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<BookingEntity> bookings;
}
