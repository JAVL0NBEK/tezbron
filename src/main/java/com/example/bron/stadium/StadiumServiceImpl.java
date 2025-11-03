package com.example.bron.stadium;

import com.example.bron.exception.ResourceNotFoundException;
import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;
import com.example.bron.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StadiumServiceImpl implements StadiumService {
    private final StadiumRepository stadiumRepository;
    private final  StadiumMapper mapper;
    private final UserRepository userRepository;

    @Override
    public StadiumResponseDto create(StadiumRequestDto dto) {
        var owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("owner_not_found",List.of(dto.getOwnerId().toString())));
        var stadium = mapper.toEntity(dto);
        stadium.setOwner(owner);
        var saved =  stadiumRepository.save(stadium);
        var response = mapper.toDto(saved);
        response.setOwnerName(owner.getUsername());
        return response;
    }

    @Override
    public StadiumResponseDto update(Long id, StadiumRequestDto dto) {
        var entity = getFindById(id);
        var owner = userRepository.findById(dto.getOwnerId())
          .orElseThrow(() -> new ResourceNotFoundException("owner_not_found",List.of(dto.getOwnerId().toString())));
        mapper.updateEntity(entity, dto);

        StadiumEntity updated = stadiumRepository.save(entity);
        var response = mapper.toDto(updated);
        response.setOwnerName(owner.getUsername());
        return response;
    }

    private StadiumEntity getFindById(Long id) {
        return stadiumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("stadium_not_found",List.of(id.toString())));
    }

    @Override
    public void delete(Long id) {
        stadiumRepository.deleteById(id);
    }

    @Override
    public StadiumResponseDto getById(Long id) {
        var entity = getFindById(id);
        return mapper.toDto(entity);
    }

    @Override
    public List<StadiumResponseDto> getAll() {
        var list = stadiumRepository.findAll();
        return list.stream()
                .map(mapper::toDto)
                .toList();
    }

}
