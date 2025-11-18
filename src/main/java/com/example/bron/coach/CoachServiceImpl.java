package com.example.bron.coach;

import com.example.bron.coach.dto.CoachRequestDto;
import com.example.bron.coach.dto.CoachResponseDto;
import com.example.bron.exception.NotFoundException;
import com.example.bron.auth.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {
  private final CoachRepository coachRepository;
  private final CoachMapper mapper;
  private final UserRepository userRepository;

  @Override
  public CoachResponseDto createStadium(CoachRequestDto dto) {
    var user = userRepository.findById(dto.getUserId()).orElseThrow(() ->
        new NotFoundException("stadium_user_not_fount",List.of(dto.getUserId().toString())));
    var coach = mapper.toEntity(dto);
    coach.setUser(user);
    var saved  = coachRepository.save(coach);
    return mapper.toDto(saved);
  }

  @Override
  public CoachResponseDto updateStadium(Long id, CoachRequestDto dto) {
    var entity = getById(id);
    mapper.updateEntity(entity,dto);
    var saved = coachRepository.save(entity);
    return mapper.toDto(saved);
  }

  @Override
  public CoachResponseDto getStadiumById(Long id) {
    return mapper.toDto(getById(id));
  }

  @Override
  public List<CoachResponseDto> getAllStadium() {
    var coaches = coachRepository.findAll();
    return coaches.stream()
        .map(mapper::toDto)
        .toList();
  }

  private CoachEntity getById(Long id) {
    return coachRepository.findById(id).orElseThrow(() -> new NotFoundException("stadium_coach_not_found",List.of(id.toString())));
  }
}
