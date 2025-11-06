package com.example.bron.match;

import com.example.bron.exception.ResourceNotFoundException;
import com.example.bron.match.dto.MatchRequestDto;
import com.example.bron.match.dto.MatchResponseDto;
import com.example.bron.stadium.StadiumRepository;
import com.example.bron.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{
  private final MatchRepository matchRepository;
  private final UserRepository userRepository;
  private final StadiumRepository stadiumRepository;
  private final MatchMapper mapper;

  @Override
  public MatchResponseDto create(MatchRequestDto dto) {
    var organizer = userRepository.findById(dto.getOrganizerId())
        .orElseThrow(() -> new ResourceNotFoundException("organizer_not_found",List.of(dto.getOrganizerId().toString())));

    var stadium = stadiumRepository.findById(dto.getStadiumId())
        .orElseThrow(() -> new ResourceNotFoundException("stadium_not_found",List.of(dto.getStadiumId().toString())));
    var matchEntity = mapper.toEntity(dto);
    matchEntity.setStadium(stadium);
    matchEntity.setOrganizer(organizer);
    var saved =  matchRepository.save(matchEntity);
    return mapper.toDto(saved);
  }

  @Override
  public MatchResponseDto update(Long id, MatchRequestDto dto) {
    return null;
  }

  @Override
  public void delete(Long id) {
    matchRepository.deleteById(id);
  }

  @Override
  public MatchResponseDto getById(Long id) {
    return mapper.toDto(getFindById(id));
  }

  @Override
  public List<MatchResponseDto> getAll() {
    var list = matchRepository.findAll();
    return list.stream()
        .map(mapper::toDto)
        .toList();
  }

  private MatchEntity getFindById(Long id) {
    return matchRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("match_not_found",List.of(id.toString())));
  }
}
