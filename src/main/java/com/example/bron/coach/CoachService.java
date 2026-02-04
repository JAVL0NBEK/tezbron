package com.example.bron.coach;

import com.example.bron.coach.dto.CoachRequestDto;
import com.example.bron.coach.dto.CoachResponseDto;
import java.util.List;

public interface CoachService {
  CoachResponseDto createCoach(CoachRequestDto dto);
  CoachResponseDto updateCoach(Long id, CoachRequestDto dto);
  CoachResponseDto getCoachById(Long id);
  List<CoachResponseDto> getAllCoach();

}
