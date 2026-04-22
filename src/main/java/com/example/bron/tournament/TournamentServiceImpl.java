package com.example.bron.tournament;

import com.example.bron.auth.security.CurrentUserService;
import com.example.bron.auth.user.UserEntity;
import com.example.bron.auth.user.UserRepository;
import com.example.bron.enums.BookingStatus;
import com.example.bron.exception.BadRequestException;
import com.example.bron.exception.ConflictException;
import com.example.bron.exception.ForbiddenException;
import com.example.bron.exception.NotFoundException;
import com.example.bron.location.DistrictEntity;
import com.example.bron.location.DistrictRepository;
import com.example.bron.notification.enums.NotificationTemplate;
import com.example.bron.notification.event.TournamentEvent;
import com.example.bron.team.TeamRepository;
import com.example.bron.tournament.dto.JoinedTeamDto;
import com.example.bron.tournament.dto.TournamentRequestDto;
import com.example.bron.tournament.dto.TournamentResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
  private final TournamentRepository tournamentRepository;
  private final TournamentTeamRepository tournamentTeamRepository;
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;
  private final DistrictRepository districtRepository;
  private final TournamentMapper mapper;
  private final ApplicationEventPublisher eventPublisher;
  private final CurrentUserService currentUserService;

  @Override
  public TournamentResponseDto create(TournamentRequestDto dto) {
    UserEntity organizer;
    DistrictEntity district;

    if (currentUserService.isSuperAdmin()) {
      organizer = userRepository.findById(dto.getOrganizerId()).orElseThrow(
          () -> new NotFoundException("tournament_organizer_id_not_found",
              List.of(String.valueOf(dto.getOrganizerId())))
      );
      district = dto.getDistrictId() == null ? null
          : districtRepository.findById(dto.getDistrictId())
              .orElseThrow(() -> new NotFoundException("district_not_found",
                  List.of(dto.getDistrictId().toString())));
    } else if (currentUserService.isDistrictAdmin() || currentUserService.isOwner()) {
      organizer = currentUserService.getCurrentUser();
      district = organizer.getDistrict();
      if (district == null) {
        throw new ForbiddenException("CURRENT_USER_HAS_NO_DISTRICT");
      }
    } else {
      throw new ForbiddenException("INSUFFICIENT_ROLE_TO_CREATE_TOURNAMENT");
    }

    var tournamentEntity = mapper.toEntity(dto);
    tournamentEntity.setOrganizer(organizer);
    tournamentEntity.setDistrict(district);
    var savedTournament = tournamentRepository.save(tournamentEntity);
    var response = mapper.toDto(savedTournament);
    response.setTeamApplied(0L);
    return response;
  }

  @Override
  public TournamentResponseDto get(Long id) {
    var tournament = tournamentRepository.findById(id).orElseThrow(
        () -> new NotFoundException("tournament_id_not_found",List.of(id.toString()))
    );
    var dto = mapper.toDto(tournament);
    dto.setTeamApplied(tournamentTeamRepository.countByTournamentId(id));
    return dto;
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
      throw new BadRequestException("SPORT_TYPE_MISMATCH", List.of(team.getSportType().name()));
    }

    // limit tekshirish
    long currentTeams =
        tournamentTeamRepository.countByTournamentId(tournamentId);

    if (currentTeams >= tournament.getMaxTeams()) {
      throw new ConflictException("TOURNAMENT_IS_FULL", List.of(tournamentId.toString()));
    }

    // oldin qo‘shilganmi?
    if (tournamentTeamRepository
        .existsByTournamentIdAndTeamId(tournamentId, teamId)) {

      throw new ConflictException("TEAM_ALREADY_REGISTERED", List.of(teamId.toString()));
    }

    TournamentTeamEntity entity = new TournamentTeamEntity();
    entity.setTournament(tournament);
    entity.setTeam(team);
    entity.setRegisteredAt(LocalDateTime.now());
    entity.setStatus(BookingStatus.CONFIRMED);

    tournamentTeamRepository.save(entity);

    // Jamoa a'zolariga notification yuborish
    team.getMembers().forEach(member ->
        eventPublisher.publishEvent(new TournamentEvent(
            member.getUser().getId(),
            NotificationTemplate.TOURNAMENT_TEAM_REGISTERED,
            tournament.getName()
        ))
    );
  }

  @Override
  @Transactional(readOnly = true)
  public List<JoinedTeamDto> getTeamJoinedTour(Long tournamentId) {
    if (!tournamentRepository.existsById(tournamentId)) {
      throw new NotFoundException("tournament_not_found", List.of(tournamentId.toString()));
    }

    return tournamentTeamRepository.getTeamJoinedTour(tournamentId).stream()
        .map(team -> {
          var memberIds = team.getMembers() == null ? List.<Long>of()
              : team.getMembers().stream()
                  .map(m -> m.getUser().getId())
                  .toList();

          return new JoinedTeamDto(
              team.getId(),
              team.getName(),
              team.getSportType(),
              team.getDescription(),
              team.getCreatedAt(),
              memberIds
          );
        })
        .toList();
  }
}
