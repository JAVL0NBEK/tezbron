package com.example.bron.team;

import com.example.bron.auth.user.UserRepository;
import com.example.bron.exception.NotFoundException;
import com.example.bron.team.dto.TeamRequestDto;
import com.example.bron.team.dto.TeamResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;
  private final TeamMapper teamMapper;
  @Override
  public TeamResponseDto createTeam(TeamRequestDto dto) {
    var user = userRepository.findById(dto.getCaptainId()).orElseThrow(() ->
        new NotFoundException("user_not_found",List.of(dto.getCaptainId().toString())));
    var entity = teamMapper.toEntity(dto);
    entity.setCaptain(user);
    var saved = teamRepository.save(entity);
    return teamMapper.toDto(saved);
  }

  @Override
  public TeamResponseDto getTeamById(Long id) {
    var team = teamRepository.findById(id).orElseThrow(() ->
        new NotFoundException("user_not_found",List.of(id.toString())));
    return teamMapper.toDto(team);
  }

  @Override
  public List<TeamResponseDto> getTeams() {
    var teams = teamRepository.findAll();
    return teams.stream()
        .map(teamMapper::toDto)
        .toList();
  }
}
