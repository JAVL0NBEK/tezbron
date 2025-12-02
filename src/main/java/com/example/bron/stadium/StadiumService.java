package com.example.bron.stadium;

import com.example.bron.enums.StadiumDuration;
import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StadiumService {
    StadiumResponseDto create(StadiumRequestDto dto);
    StadiumResponseDto update(Long id, StadiumRequestDto dto);
    void delete(Long id);
    List<StadiumResponseDto> getById(Long id, LocalDate date, StadiumDuration duration);
    Page<StadiumResponseDto> getAll(StadiumFilterParams filterParams, Pageable pageable);
}
