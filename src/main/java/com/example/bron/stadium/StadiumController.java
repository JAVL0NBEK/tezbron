package com.example.bron.stadium;

import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StadiumController implements StadiumApi {
    private final StadiumService stadiumService;

    @Override
    public ResponseEntity<StadiumResponseDto> create(StadiumRequestDto dto) {
        return ResponseEntity.ok(stadiumService.create(dto));
    }

    @Override
    public ResponseEntity<StadiumResponseDto> update(Long id, StadiumRequestDto dto) {
        return ResponseEntity.ok(stadiumService.update(id, dto));
    }

    @Override
    public ResponseEntity<StadiumResponseDto> getById(Long id) {
        return ResponseEntity.ok(stadiumService.getById(id));
    }

    @Override
    public ResponseEntity<List<StadiumResponseDto>> getAll() {
        return ResponseEntity.ok(stadiumService.getAll());
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        stadiumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
