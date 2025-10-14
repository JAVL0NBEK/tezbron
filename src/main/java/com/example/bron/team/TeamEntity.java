package com.example.bron.team;

import com.example.bron.user.UserEntity;
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

    @ManyToOne
    @JoinColumn(name = "captain_id")
    private UserEntity captain;

    @Column(nullable = false)
    private String sportType;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", name = "members")
    private List<Long> members;

    private String description;

    @Column(updatable = false)
    private LocalDateTime createdAt;

}
