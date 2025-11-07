package com.example.bron.match;

import com.example.bron.common.BaseResponse;
import com.example.bron.match.dto.MatchRequestDto;
import com.example.bron.match.dto.MatchResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController implements MatchApi {
    private final MatchService matchService;

  @Override
  public ResponseEntity<BaseResponse<MatchResponseDto>> create(MatchRequestDto dto) {
    var response = matchService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.created(response));
  }

  @Override
  public ResponseEntity<BaseResponse<MatchResponseDto>> update(Long id, MatchRequestDto dto) {
    var response = matchService.update(id, dto);
    return ResponseEntity.ok(BaseResponse.ok(response));
  }

  @Override
  public ResponseEntity<BaseResponse<MatchResponseDto>> getById(Long id) {
    var response = matchService.getById(id);
    return ResponseEntity.ok(BaseResponse.ok(response));
  }

  @Override
  public ResponseEntity<BaseResponse<List<MatchResponseDto>>> getAll() {
    var list = matchService.getAll();
    return ResponseEntity.ok(BaseResponse.ok(list));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> delete(Long id) {
    matchService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(BaseResponse.noContent("Match deleted successfully"));
  }
}
