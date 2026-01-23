package com.example.bron.team;

import com.example.bron.auth.user.UserRepository;
import com.example.bron.enums.Role;
import com.example.bron.exception.NotFoundException;
import com.example.bron.team.dto.JoinTeamRequest;
import com.example.bron.team.dto.TeamRequestDto;
import com.example.bron.team.dto.TeamResponseDto;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
  private final TeamRepository teamRepository;
  private final TeamMemberRepository teamMemberRepository;
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
  @Transactional
  public TeamResponseDto joinTeam(Long teamId, JoinTeamRequest dto) {
    var team = teamRepository.findById(teamId).orElseThrow(() ->
        new NotFoundException("team_not_found",List.of(teamId.toString())));

    if (teamMemberRepository.countByTeamId(teamId) >= team.getMaxMembers()) {
      throw new NotFoundException("team_is_full",List.of(teamId.toString()));
    }

    if (teamMemberRepository.existsByTeamIdAndUserId(teamId, dto.userId())) {
      throw new NotFoundException("user_already_in_team",List.of(dto.userId().toString()));
    }

    var user = userRepository.findById(dto.userId())
        .orElseThrow(() -> new NotFoundException("user_not_found",List.of(dto.userId().toString())));

    TeamMemberEntity member = new TeamMemberEntity();
    member.setTeam(team);
    member.setUser(user);
    member.setRole(Role.PLAYER);
    member.setJoinedAt(LocalDateTime.now());

    teamMemberRepository.save(member);

    return teamMapper.toDto(team);
  }

  @Override
  @Transactional
  public void leaveTeam(Long teamId, JoinTeamRequest dto) {
    TeamMemberEntity member = teamMemberRepository
        .findByTeamIdAndUserId(teamId, dto.userId())
        .orElseThrow(() -> new NotFoundException("user_not_in_team",List.of(dto.userId().toString())));

    if (member.getRole() == Role.CAPTAIN) {
      throw new NotFoundException("captain_cannot_leave_team",List.of(dto.userId().toString()));
    }

    teamMemberRepository.delete(member);

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
