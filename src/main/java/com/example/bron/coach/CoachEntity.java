package com.example.bron.coach;

import com.example.bron.user.UserEntity;
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

    @Column(columnDefinition = "jsonb")
    private String availability;

    @Column(columnDefinition = "jsonb")
    private String reviews;

}
