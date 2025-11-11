package com.example.bron.tournament;

import com.example.bron.enums.TournamentStatus;
import com.example.bron.auth.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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

    private String sportType;

    private Integer maxTeams;
    private Double entryFee;

    private String rules;

    @Enumerated(EnumType.STRING)
    private TournamentStatus status;

}
