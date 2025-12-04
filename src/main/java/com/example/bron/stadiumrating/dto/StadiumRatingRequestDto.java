package com.example.bron.stadiumrating.dto;

import com.example.bron.stadium.StadiumEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StadiumRatingRequestDto {
  private Long stadiumId;

  private Long userId;

  private Integer rating; // 1–5

  private String comment;

}
