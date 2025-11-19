package com.example.bron.tournament;

import com.example.bron.tournament.dto.TournamentRequestDto;
import com.example.bron.tournament.dto.TournamentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TournamentMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "organizer", ignore = true)
  TournamentEntity toEntity(TournamentRequestDto dto);

  TournamentResponseDto toDto(TournamentEntity entity);

}
