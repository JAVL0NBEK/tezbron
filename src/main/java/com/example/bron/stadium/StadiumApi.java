package com.example.bron.stadium;

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
    ResponseEntity<StadiumResponseDto> create(@RequestBody StadiumRequestDto stadiumRequestDto);

    @PutMapping("/{id}")
    ResponseEntity<StadiumResponseDto> update(@PathVariable Long id,
        @RequestBody StadiumRequestDto dto);

    @GetMapping("/{id}")
    ResponseEntity<StadiumResponseDto> getById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<StadiumResponseDto>> getAll();

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
