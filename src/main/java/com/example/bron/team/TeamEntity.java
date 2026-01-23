package com.example.bron.team;

import com.example.bron.auth.user.UserEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "captain_id")
    private UserEntity captain;

    @Column(nullable = false)
    private Long maxMembers;

    @Column(nullable = false)
    private String sportType;

    @OneToMany(
      mappedBy = "team",
      cascade = CascadeType.ALL,
      orphanRemoval = true
    )
    private List<TeamMemberEntity> members;

    private String description;

    @Column(updatable = false)
    private LocalDateTime createdAt;

}
