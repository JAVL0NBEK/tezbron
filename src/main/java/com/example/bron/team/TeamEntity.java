package com.example.bron.team;

import com.example.bron.auth.user.UserEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

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
    private String sportType;

    @ElementCollection
    @CollectionTable(
      name = "team_members",
      joinColumns = @JoinColumn(name = "team_id")
    )
    @Column(name = "member_id")
    private List<Long> memberIds;

    private String description;

    @Column(updatable = false)
    private LocalDateTime createdAt;

}
