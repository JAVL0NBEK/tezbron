package com.example.bron.stadiumrating.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StadiumRatingResponseDto {
  private Long id;
  private Long stadiumId;
  private Long userId;
  private Integer rating; // 1–5
  private String comment;
  private String stadiumName;
  private String userFullName;
}
