package com.example.bron.team;

import com.example.bron.enums.SportType;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private Long maxMembers;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SportType sportType;

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
