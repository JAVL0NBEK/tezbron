package com.example.bron.user;

import com.example.bron.booking.BookingEntity;
import com.example.bron.enums.Role;
import com.example.bron.team.TeamEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String phone;
    private String fullName;
    private String profileImageUrl;

    @Column(columnDefinition = "jsonb")
    private String location;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<BookingEntity> bookings;

    @OneToMany(mappedBy = "captain")
    private List<TeamEntity> teams;

}
