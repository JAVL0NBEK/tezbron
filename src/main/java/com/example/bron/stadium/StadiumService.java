package com.example.bron.stadium;

import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;

import java.util.List;

public interface StadiumService {
    StadiumResponseDto create(StadiumRequestDto dto);
    StadiumResponseDto update(Long id, StadiumRequestDto dto);
    void delete(Long id);
    StadiumResponseDto getById(Long id);
    List<StadiumResponseDto> getAll();
}
