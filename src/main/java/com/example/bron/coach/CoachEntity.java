package com.example.bron.coach;

import com.example.bron.auth.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coaches")
public class CoachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    private String specialty;

    private Integer experienceYears;

    private Double hourlyRate;

    private String availability;

    private String reviews;

}
