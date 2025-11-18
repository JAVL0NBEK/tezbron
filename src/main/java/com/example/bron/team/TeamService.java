package com.example.bron.team;

import com.example.bron.team.dto.TeamRequestDto;
import com.example.bron.team.dto.TeamResponseDto;
import java.util.List;

public interface TeamService {

  TeamResponseDto createTeam(TeamRequestDto dto);
  TeamResponseDto getTeamById(Long id);
  List<TeamResponseDto> getTeams();

}
