package com.example.bron.coach.dto;

import com.example.bron.auth.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoachResponseDto {
  private Long id;
  private String coachName;
  private String specialty;
  private Integer experienceYears;
  private Double hourlyRate;
  private String availability;
  private String reviews;

}
