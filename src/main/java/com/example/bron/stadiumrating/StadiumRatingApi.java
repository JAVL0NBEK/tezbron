package com.example.bron.stadiumrating;

import com.example.bron.common.BaseResponse;
import com.example.bron.stadiumrating.dto.RatingAvgAndCountResponseDto;
import com.example.bron.stadiumrating.dto.StadiumRatingRequestDto;
import com.example.bron.stadiumrating.dto.StadiumRatingResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/stadium-ratings")
@Tag(name = "Stadium Ratings Management APIs", description = "Endpoints for managing stadium ratings")
public interface StadiumRatingApi {

  @PostMapping("/create")
  ResponseEntity<BaseResponse<StadiumRatingResponseDto>> createStadiumRating(@RequestBody StadiumRatingRequestDto dto);

  @GetMapping("{stadiumId}")
  ResponseEntity<BaseResponse<RatingAvgAndCountResponseDto>> getRatings(@PathVariable Long stadiumId);

}
