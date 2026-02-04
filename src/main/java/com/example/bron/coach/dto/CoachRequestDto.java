package com.example.bron.coach.dto;

import com.example.bron.enums.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoachRequestDto {
  private Long userId;
  private String specialty;
  private Integer experienceYears;
  private Double hourlyRate;
  private String availability;
  private String reviews;
  private Duration duration;

}
