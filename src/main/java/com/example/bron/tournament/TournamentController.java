package com.example.bron.tournament;

import com.example.bron.common.BaseResponse;
import com.example.bron.tournament.dto.TournamentRequestDto;
import com.example.bron.tournament.dto.TournamentResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TournamentController implements TournamentApi {
  private final TournamentService tournamentService;

  @Override
  public ResponseEntity<BaseResponse<TournamentResponseDto>> create(
      TournamentRequestDto tournamentRequestDto) {
    var tournament = tournamentService.create(tournamentRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.created(tournament));
  }

  @Override
  public ResponseEntity<BaseResponse<TournamentResponseDto>> get(Long id) {
    var tournament = tournamentService.get(id);
    return ResponseEntity.ok(BaseResponse.ok(tournament));
  }

  @Override
  public ResponseEntity<BaseResponse<List<TournamentResponseDto>>> getAll() {
    var tournaments = tournamentService.getAll();
    return ResponseEntity.ok(BaseResponse.ok(tournaments));
  }
}
