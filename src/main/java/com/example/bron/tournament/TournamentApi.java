package com.example.bron.tournament;

import com.example.bron.common.BaseResponse;
import com.example.bron.tournament.dto.JoinedTeamDto;
import com.example.bron.tournament.dto.TournamentRequestDto;
import com.example.bron.tournament.dto.TournamentResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("v1/tournaments")
@Tag(name = "Tournament Management APIs", description = "Endpoints for managing tournament")
public interface TournamentApi {

  @PreAuthorize("hasAnyRole('SUPER_ADMIN','DISTRICT_ADMIN','OWNER')")
  @PostMapping("/create")
  ResponseEntity<BaseResponse<TournamentResponseDto>> create(@RequestBody TournamentRequestDto tournamentRequestDto);

  @GetMapping("/{id}")
  ResponseEntity<BaseResponse<TournamentResponseDto>> get(@PathVariable Long id);

  @GetMapping
  ResponseEntity<BaseResponse<List<TournamentResponseDto>>> getAll(TournamentFilterParams filterParams);

  @PostMapping("/add-team-to-tournament")
  ResponseEntity<BaseResponse<Void>> addTeamToTournament(
      @RequestParam Long tournamentId,
      @RequestParam Long teamId
  );

  @GetMapping("/joined-teams/{tournamentId}")
  ResponseEntity<BaseResponse<List<JoinedTeamDto>>> getTeamJoinedTour(@PathVariable Long tournamentId);

}
