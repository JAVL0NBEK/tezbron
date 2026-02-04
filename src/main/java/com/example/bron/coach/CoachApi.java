package com.example.bron.coach;

import com.example.bron.coach.dto.CoachRequestDto;
import com.example.bron.coach.dto.CoachResponseDto;
import com.example.bron.common.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/coaches")
@Tag(name = "Coaches Management APIs", description = "Endpoints for managing Coaches")
public interface CoachApi {

  @PostMapping("/create")
  ResponseEntity<BaseResponse<CoachResponseDto>> createCoach(CoachRequestDto dto);

  @PutMapping("/{id}")
  ResponseEntity<BaseResponse<CoachResponseDto>> updateCoach(@PathVariable Long id, CoachRequestDto dto);

  @GetMapping("/{id}")
  ResponseEntity<BaseResponse<CoachResponseDto>> getCoachById(@PathVariable Long id);

  @GetMapping
  ResponseEntity<BaseResponse<List<CoachResponseDto>>> getCoach();

}
