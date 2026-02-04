package com.example.bron.team;

import com.example.bron.common.BaseResponse;
import com.example.bron.team.dto.JoinTeamRequest;
import com.example.bron.team.dto.TeamRequestDto;
import com.example.bron.team.dto.TeamResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/teams")
@Tag(name = "Teams Management APIs", description = "Endpoints for managing teams")
public interface TeamApi {

  @PostMapping("/create")
  ResponseEntity<BaseResponse<TeamResponseDto>> createTeam(@RequestBody TeamRequestDto dto);

  @PostMapping("/{teamId}/members")
  ResponseEntity<BaseResponse<TeamResponseDto>> joinTeam(@PathVariable Long teamId, @RequestBody JoinTeamRequest dto);

  @DeleteMapping("/{teamId}/leave/{userId}")
  ResponseEntity<BaseResponse<Void>> leaveTeam(@PathVariable Long teamId, @RequestBody JoinTeamRequest dto);

  @GetMapping
  ResponseEntity<BaseResponse<List<TeamResponseDto>>> getMatches();

  @GetMapping("/{id}")
  ResponseEntity<BaseResponse<TeamResponseDto>> getTeam(@PathVariable Long id);

}
