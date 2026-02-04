package com.example.bron.coach;

import com.example.bron.coach.dto.CoachRequestDto;
import com.example.bron.coach.dto.CoachResponseDto;
import com.example.bron.common.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoachController implements CoachApi {
  private final CoachService service;

  @Override
  public ResponseEntity<BaseResponse<CoachResponseDto>> createCoach(CoachRequestDto dto) {
    var coach = service.createCoach(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.created(coach));
  }

  @Override
  public ResponseEntity<BaseResponse<CoachResponseDto>> updateCoach(Long id,
      CoachRequestDto dto) {
    var coach = service.updateCoach(id, dto);
    return ResponseEntity.ok(BaseResponse.ok(coach));
  }

  @Override
  public ResponseEntity<BaseResponse<CoachResponseDto>> getCoachById(Long id) {
    var coach = service.getCoachById(id);
    return ResponseEntity.ok(BaseResponse.ok(coach));
  }

  @Override
  public ResponseEntity<BaseResponse<List<CoachResponseDto>>> getCoach() {
    var coaches = service.getAllCoach();
    return ResponseEntity.ok(BaseResponse.ok(coaches));
  }
}
