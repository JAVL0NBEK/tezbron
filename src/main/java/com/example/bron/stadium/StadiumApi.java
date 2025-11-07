package com.example.bron.stadium;

import com.example.bron.common.BaseResponse;
import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
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
  ResponseEntity<BaseResponse<StadiumResponseDto>> getById(@PathVariable Long id);

  @GetMapping
  ResponseEntity<BaseResponse<List<StadiumResponseDto>>> getAll();

  @DeleteMapping("/{id}")
  ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id);
}
