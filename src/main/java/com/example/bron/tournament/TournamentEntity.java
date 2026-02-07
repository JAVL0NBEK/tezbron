package com.example.bron.tournament;

import com.example.bron.enums.SportType;
import com.example.bron.enums.TournamentStatus;
import com.example.bron.auth.user.UserEntity;
import com.example.bron.stadium.dto.LocationDto;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Entity
@Table(name = "tournaments")
public class TournamentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organizer_id")
    private UserEntity organizer;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private SportType sportType;

    private Integer maxTeams;

    private Integer teamApplied;

    private Double entryFee;

    private String rules;

    @Enumerated(EnumType.STRING)
    private TournamentStatus status;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private LocationDto location;
    private String address;
    private String prizes;

}
