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
  public ResponseEntity<BaseResponse<CoachResponseDto>> createStadium(CoachRequestDto dto) {
    var coach = service.createStadium(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.created(coach));
  }

  @Override
  public ResponseEntity<BaseResponse<CoachResponseDto>> updateStadium(Long id,
      CoachRequestDto dto) {
    var coach = service.updateStadium(id, dto);
    return ResponseEntity.ok(BaseResponse.ok(coach));
  }

  @Override
  public ResponseEntity<BaseResponse<CoachResponseDto>> getStadiumById(Long id) {
    var coach = service.getStadiumById(id);
    return ResponseEntity.ok(BaseResponse.ok(coach));
  }

  @Override
  public ResponseEntity<BaseResponse<List<CoachResponseDto>>> getStadiums() {
    var coaches = service.getAllStadium();
    return ResponseEntity.ok(BaseResponse.ok(coaches));
  }
}
