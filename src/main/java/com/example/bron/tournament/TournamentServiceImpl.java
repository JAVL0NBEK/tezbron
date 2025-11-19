package com.example.bron.tournament;

import com.example.bron.auth.user.UserRepository;
import com.example.bron.exception.NotFoundException;
import com.example.bron.tournament.dto.TournamentRequestDto;
import com.example.bron.tournament.dto.TournamentResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
  private final TournamentRepository tournamentRepository;
  private final UserRepository userRepository;
  private final TournamentMapper mapper;

  @Override
  public TournamentResponseDto create(TournamentRequestDto dto) {
    var tournamentEntity = mapper.toEntity(dto);
    var organizer = userRepository.findById(dto.getOrganizerId()).orElseThrow(
        () -> new NotFoundException("tournament_organizer_id_not_found",List.of(dto.getOrganizerId().toString()))
    );
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
  public List<TournamentResponseDto> getAll() {
    var tournaments = tournamentRepository.findAll();
    return tournaments.stream().map(mapper::toDto).toList();
  }
}
