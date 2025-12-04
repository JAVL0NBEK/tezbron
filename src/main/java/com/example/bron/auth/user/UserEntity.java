package com.example.bron.auth.user;

import com.example.bron.booking.BookingEntity;
import com.example.bron.enums.Role;
import com.example.bron.team.TeamEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

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

    @OneToMany(mappedBy = "captain")
    @JsonIgnore
    private List<TeamEntity> teams;

}
