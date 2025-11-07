package com.example.bron.stadium;

import com.example.bron.common.BaseResponse;
import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StadiumController implements StadiumApi {
    private final StadiumService stadiumService;

  @Override
  public ResponseEntity<BaseResponse<StadiumResponseDto>> create(StadiumRequestDto dto) {
    StadiumResponseDto response = stadiumService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.created(response));
  }

  @Override
  public ResponseEntity<BaseResponse<StadiumResponseDto>> update(Long id, StadiumRequestDto dto) {
    StadiumResponseDto response = stadiumService.update(id, dto);
    return ResponseEntity.ok(BaseResponse.ok(response));
  }

  @Override
  public ResponseEntity<BaseResponse<StadiumResponseDto>> getById(Long id) {
    StadiumResponseDto response = stadiumService.getById(id);
    return ResponseEntity.ok(BaseResponse.ok(response));
  }

  @Override
  public ResponseEntity<BaseResponse<List<StadiumResponseDto>>> getAll() {
    List<StadiumResponseDto> list = stadiumService.getAll();
    return ResponseEntity.ok(BaseResponse.ok(list));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> delete(Long id) {
    stadiumService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(BaseResponse.noContent("Stadium deleted successfully"));
  }
}
