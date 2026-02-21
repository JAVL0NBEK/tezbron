package com.example.bron.stadium;

import com.example.bron.common.BaseResponse;
import com.example.bron.enums.StadiumDuration;
import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/stadiums")
@Tag(name = "Stadium Management APIs", description = "Endpoints for managing stadiums")
public interface StadiumApi {

  @PostMapping("/create")
  ResponseEntity<BaseResponse<StadiumResponseDto>> create(@RequestBody StadiumRequestDto stadiumRequestDto);

  @PutMapping("/{id}")
  ResponseEntity<BaseResponse<StadiumResponseDto>> update(@PathVariable Long id,
      @RequestBody StadiumRequestDto dto);

  @GetMapping("/{id}")
  ResponseEntity<BaseResponse<List<StadiumResponseDto>>> getById(
      @PathVariable Long id,
      @RequestParam("date") LocalDate date,
      @RequestParam("duration") StadiumDuration duration
  );

  @GetMapping
  ResponseEntity<BaseResponse<Page<StadiumResponseDto>>> getAll(StadiumFilterParams filterParams, Pageable pageable);

  @DeleteMapping("/{id}")
  ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id);

  @PutMapping("/{id}/{isFavorite}")
  ResponseEntity<BaseResponse<StadiumResponseDto>> updateFavorite(@PathVariable Long id,
      @PathVariable Boolean isFavorite);
}
