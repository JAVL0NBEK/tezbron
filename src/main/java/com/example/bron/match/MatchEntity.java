package com.example.bron.match;

import com.example.bron.enums.MatchStatus;
import com.example.bron.stadium.StadiumEntity;
import com.example.bron.user.UserEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "matches")
@Getter
@Setter
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id")
    private UserEntity organizer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stadium_id")
    private StadiumEntity stadium;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    private Integer duration;
    private Integer maxPlayers;
    private Integer currentPlayers;
    private Double pricePerPlayer;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", name = "participants")
    private List<Long> participants;

    @Column(columnDefinition = "jsonb")
    private String location;

}
