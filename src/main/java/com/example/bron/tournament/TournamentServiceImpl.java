package com.example.bron.tournament;

import com.example.bron.auth.user.UserRepository;
import com.example.bron.enums.BookingStatus;
import com.example.bron.exception.NotFoundException;
import com.example.bron.team.TeamRepository;
import com.example.bron.tournament.dto.TournamentRequestDto;
import com.example.bron.tournament.dto.TournamentResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
  private final TournamentRepository tournamentRepository;
  private final TournamentTeamRepository tournamentTeamRepository;
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;
  private final TournamentMapper mapper;

  @Override
  public TournamentResponseDto create(TournamentRequestDto dto) {
    var organizer = userRepository.findById(dto.getOrganizerId()).orElseThrow(
        () -> new NotFoundException("tournament_organizer_id_not_found",List.of(dto.getOrganizerId().toString()))
    );
    var tournamentEntity = mapper.toEntity(dto);
    tournamentEntity.setOrganizer(organizer);
    var savedTournament = tournamentRepository.save(tournamentEntity);
    return mapper.toDto(savedTournament);
  }

  @Override
  public TournamentResponseDto get(Long id) {
    var tournament = tournamentRepository.findById(id).orElseThrow(
        () -> new NotFoundException("tournament_id_not_found",List.of(id.toString()))
    );
    return mapper.toDto(tournament);
  }

  @Override
  public List<TournamentResponseDto> getAll(TournamentFilterParams filterParams) {
    return tournamentRepository.getAll(filterParams);
  }

  @Transactional
  public void addTeamToTournament(Long tournamentId, Long teamId) {

    var tournament = tournamentRepository.findById(tournamentId)
        .orElseThrow(() -> new NotFoundException("tournament_not_found", List.of(tournamentId.toString())));

    var team = teamRepository.findById(teamId)
        .orElseThrow(() -> new NotFoundException("team_not_found", List.of(teamId.toString())));

    // sportType mosligini tekshirish
    if (team.getSportType() != tournament.getSportType()) {
      throw new NotFoundException("sport_type_mismatch", List.of(team.getSportType().name()));
    }

    // limit tekshirish
    long currentTeams =
        tournamentTeamRepository.countByTournamentId(tournamentId);

    if (currentTeams >= tournament.getMaxTeams()) {
      throw new NotFoundException("tournament_is_full", List.of(tournamentId.toString()));
    }

    // oldin qo‘shilganmi?
    if (tournamentTeamRepository
        .existsByTournamentIdAndTeamId(tournamentId, teamId)) {

      throw new NotFoundException("team_already_registered", List.of(teamId.toString()));
    }

    TournamentTeamEntity entity = new TournamentTeamEntity();
    entity.setTournament(tournament);
    entity.setTeam(team);
    entity.setRegisteredAt(LocalDateTime.now());
    entity.setStatus(BookingStatus.CONFIRMED);

    tournamentTeamRepository.save(entity);

    tournament.setTeamApplied(
        (tournament.getTeamApplied() == null ? 0 : tournament.getTeamApplied()) + 1
    );
  }
}
