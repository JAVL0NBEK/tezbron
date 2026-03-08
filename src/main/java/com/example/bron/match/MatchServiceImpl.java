package com.example.bron.match;

import com.example.bron.enums.ParticipantStatus;
import com.example.bron.exception.NotFoundException;
import com.example.bron.match.dto.JoinMatchRequestDto;
import com.example.bron.match.dto.MatchRequestDto;
import com.example.bron.match.dto.MatchResponseDto;
import com.example.bron.stadium.StadiumRepository;
import com.example.bron.auth.user.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService{
  private final MatchRepository matchRepository;
  private final MatchParticipantRepository matchParticipantRepository;
  private final UserRepository userRepository;
  private final StadiumRepository stadiumRepository;
  private final MatchMapper mapper;

  @Override
  public MatchResponseDto create(MatchRequestDto dto) {
    var organizer = userRepository.findById(dto.getOrganizerId())
        .orElseThrow(() -> new NotFoundException("organizer_not_found",List.of(dto.getOrganizerId().toString())));

    var stadium = stadiumRepository.findById(dto.getStadiumId())
        .orElseThrow(() -> new NotFoundException("stadium_not_found",List.of(dto.getStadiumId().toString())));
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
    var match = getFindById(id);
    match.setCurrentPlayers(match.getParticipants().size());
    return mapper.toDto(match);
  }

  @Override
  public List<MatchResponseDto> getAll(MatchFilterParams filterParams) {
    return matchRepository.getAll(filterParams);
  }

  @Override
  public MatchResponseDto joinMatch(Long matchId, JoinMatchRequestDto dto) {
    var match = matchRepository.findById(matchId).orElseThrow(() ->
        new NotFoundException("match_not_found",List.of(matchId.toString())));
    var participant = userRepository.findById(dto.userId()).orElseThrow(
        () -> new NotFoundException("participant_not_found",List.of(dto.userId().toString())));
    if (matchParticipantRepository.countByMatchId(matchId) >= match.getMaxPlayers()) {
      throw new NotFoundException("match_is_full",List.of(matchId.toString()));
    }

    if (matchParticipantRepository.existsByMatchIdAndUserId(matchId,dto.userId())) {
      throw new NotFoundException("match_is_user_already_exist",List.of(matchId.toString()));
    }

    var matchParticipant = new MatchParticipantEntity();
    matchParticipant.setMatch(match);
    matchParticipant.setUser(participant);
    matchParticipant.setJoinedAt(LocalDateTime.now());
    matchParticipant.setStatus(ParticipantStatus.REQUESTED);
    matchParticipantRepository.save(matchParticipant);
    return mapper.toDto(match);
  }

  @Override
  public void leaveMatch(Long matchId, Long userId) {
    var match = matchRepository.findById(matchId).orElseThrow(() ->
        new NotFoundException("match_not_found",List.of(matchId.toString())));

    var matchParticipant =  matchParticipantRepository.findByMatchIdAndUserId(matchId,userId)
        .orElseThrow(() -> new NotFoundException("participant_not_in_match",List.of(matchId.toString())));

    if (!match.getOrganizer().getId().equals(userId)) {
      throw new NotFoundException("organizer_cannot_leave_leave",List.of(userId.toString()));
    }

    matchParticipantRepository.delete(matchParticipant);
  }

  private MatchEntity getFindById(Long id) {
    return matchRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("match_not_found",List.of(id.toString())));
  }
}
