package com.example.bron.coach;

import com.example.bron.coach.dto.CoachRequestDto;
import com.example.bron.coach.dto.CoachResponseDto;
import java.util.List;

public interface CoachService {
  CoachResponseDto createStadium(CoachRequestDto dto);
  CoachResponseDto updateStadium(Long id, CoachRequestDto dto);
  CoachResponseDto getStadiumById(Long id);
  List<CoachResponseDto> getAllStadium();

}
