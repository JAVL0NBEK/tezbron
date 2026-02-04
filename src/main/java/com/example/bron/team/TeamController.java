package com.example.bron.team;

import com.example.bron.common.BaseResponse;
import com.example.bron.team.dto.JoinTeamRequest;
import com.example.bron.team.dto.TeamRequestDto;
import com.example.bron.team.dto.TeamResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController implements TeamApi {
  private final TeamService teamService;

  @Override
  public ResponseEntity<BaseResponse<TeamResponseDto>> createTeam(TeamRequestDto dto) {
    var response = teamService.createTeam(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.created(response));
  }

  @Override
  public ResponseEntity<BaseResponse<TeamResponseDto>> joinTeam(Long teamId, JoinTeamRequest dto) {
    var response = teamService.joinTeam(teamId, dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.created(response));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> leaveTeam(Long teamId, JoinTeamRequest dto) {
    teamService.leaveTeam(teamId, dto);
    return ResponseEntity.ok(BaseResponse.noContent("Success"));
  }

  @Override
  public ResponseEntity<BaseResponse<List<TeamResponseDto>>> getMatches() {
    var teams = teamService.getTeams();
    return ResponseEntity.ok(BaseResponse.ok(teams));
  }

  @Override
  public ResponseEntity<BaseResponse<TeamResponseDto>> getTeam(Long id) {
    var team = teamService.getTeamById(id);
    return ResponseEntity.ok(BaseResponse.ok(team));
  }
}
