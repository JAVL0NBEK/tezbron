package com.example.bron.match;

import com.example.bron.common.BaseResponse;
import com.example.bron.match.dto.MatchRequestDto;
import com.example.bron.match.dto.MatchResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/matches")
@Tag(name = "Matches Management APIs", description = "Endpoints for managing matches")
public interface MatchApi {
  @PostMapping("/create")
  ResponseEntity<BaseResponse<MatchResponseDto>> create(@RequestBody MatchRequestDto dto);

  @PutMapping("/{id}")
  ResponseEntity<BaseResponse<MatchResponseDto>> update(@PathVariable Long id,
      @RequestBody MatchRequestDto dto);

  @GetMapping("/{id}")
  ResponseEntity<BaseResponse<MatchResponseDto>> getById(@PathVariable Long id);

  @GetMapping
  ResponseEntity<BaseResponse<List<MatchResponseDto>>> getAll();

  @DeleteMapping("/{id}")
  ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id);
}
