package com.example.bron.stadiumrating;

import com.example.bron.common.BaseResponse;
import com.example.bron.stadiumrating.dto.RatingAvgAndCountResponseDto;
import com.example.bron.stadiumrating.dto.StadiumRatingRequestDto;
import com.example.bron.stadiumrating.dto.StadiumRatingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StadiumRatingController implements StadiumRatingApi{
  private final StadiumRatingService service;

  @Override
  public ResponseEntity<BaseResponse<StadiumRatingResponseDto>> createStadiumRating(StadiumRatingRequestDto dto) {
    var rating = service.createStadiumRating(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.created(rating));
  }

  @Override
  public ResponseEntity<BaseResponse<RatingAvgAndCountResponseDto>> getRatings(Long stadiumId) {
    var rating = service.getRatings(stadiumId);
    return ResponseEntity.ok(BaseResponse.ok(rating));
  }
}
