package com.example.bron.stadiumrating.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingAvgAndCountResponseDto {
  private Double averageRating;
  private Long ratingCount;
  private List<StadiumRatingResponseDto> comments;
}
