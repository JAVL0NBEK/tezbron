package com.example.bron.tournament;

import com.example.bron.common.BaseResponse;
import com.example.bron.tournament.dto.TournamentRequestDto;
import com.example.bron.tournament.dto.TournamentResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1/tournaments")
@Tag(name = "Tournament Management APIs", description = "Endpoints for managing tournament")
public interface TournamentApi {

  @PostMapping("/create")
  ResponseEntity<BaseResponse<TournamentResponseDto>> create(@RequestBody TournamentRequestDto tournamentRequestDto);

  @GetMapping("/{id}")
  ResponseEntity<BaseResponse<TournamentResponseDto>> get(@PathVariable Long id);

  @GetMapping
  ResponseEntity<BaseResponse<List<TournamentResponseDto>>> getAll();

}
