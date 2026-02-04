package com.example.bron.tournament;

import com.example.bron.tournament.dto.TournamentRequestDto;
import com.example.bron.tournament.dto.TournamentResponseDto;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

public interface TournamentService {

  TournamentResponseDto create(@RequestBody TournamentRequestDto tournamentRequestDto);
  TournamentResponseDto get(Long id);
  List<TournamentResponseDto> getAll();

}
