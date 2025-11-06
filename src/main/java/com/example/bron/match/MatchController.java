package com.example.bron.match;

import com.example.bron.match.dto.MatchRequestDto;
import com.example.bron.match.dto.MatchResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController implements MatchApi {
    private final MatchService matchService;

  @Override
  public ResponseEntity<MatchResponseDto> create(MatchRequestDto dto) {
    return ResponseEntity.ok(matchService.create(dto));
  }

  @Override
  public ResponseEntity<MatchResponseDto> update(Long id, MatchRequestDto dto) {
    return ResponseEntity.ok(matchService.update(id, dto));
  }

  @Override
  public ResponseEntity<MatchResponseDto> getById(Long id) {
    return ResponseEntity.ok(matchService.getById(id));
  }

  @Override
  public ResponseEntity<List<MatchResponseDto>> getAll() {
    return ResponseEntity.ok(matchService.getAll());
  }

  @Override
  public ResponseEntity<Void> delete(Long id) {
    matchService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
