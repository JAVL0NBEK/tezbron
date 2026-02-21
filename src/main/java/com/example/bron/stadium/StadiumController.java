package com.example.bron.stadium;

import com.example.bron.common.BaseResponse;
import com.example.bron.enums.StadiumDuration;
import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;
import java.time.LocalDate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public ResponseEntity<BaseResponse<List<StadiumResponseDto>>> getById(Long id, LocalDate date, StadiumDuration duration) {
    var response = stadiumService.getById(id, date, duration);
    return ResponseEntity.ok(BaseResponse.ok(response));
  }

  @Override
  public ResponseEntity<BaseResponse<Page<StadiumResponseDto>>> getAll(StadiumFilterParams filterParams, Pageable pageable) {
    var list = stadiumService.getAll(filterParams, pageable);
    return ResponseEntity.ok(BaseResponse.ok(list));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> delete(Long id) {
    stadiumService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(BaseResponse.noContent("Stadium deleted successfully"));
  }

  @Override
  public ResponseEntity<BaseResponse<StadiumResponseDto>> updateFavorite(Long id,
      Boolean isFavorite) {
    var response = stadiumService.updateFavorite(id, isFavorite);
    return ResponseEntity.ok(BaseResponse.ok(response));
  }
}
