package com.example.bron.match;

import com.example.bron.match.dto.MatchRequestDto;
import com.example.bron.match.dto.MatchResponseDto;
import java.util.List;

public interface MatchService {
  MatchResponseDto create(MatchRequestDto dto);
  MatchResponseDto update(Long id, MatchRequestDto dto);
  void delete(Long id);
  MatchResponseDto getById(Long id);
  List<MatchResponseDto> getAll();
}
