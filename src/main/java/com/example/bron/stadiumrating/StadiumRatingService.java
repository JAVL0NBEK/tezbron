package com.example.bron.stadiumrating;

import com.example.bron.stadiumrating.dto.RatingAvgAndCountResponseDto;
import com.example.bron.stadiumrating.dto.StadiumRatingRequestDto;
import com.example.bron.stadiumrating.dto.StadiumRatingResponseDto;

public interface StadiumRatingService {
  StadiumRatingResponseDto createStadiumRating(StadiumRatingRequestDto dto);

  RatingAvgAndCountResponseDto getRatings(Long stadiumId);

}
