package com.example.bron.tournament;

import com.example.bron.tournament.dto.TournamentRequestDto;
import com.example.bron.tournament.dto.TournamentResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TournamentMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "organizer", ignore = true)
  @Mapping(target = "district", ignore = true)
  TournamentEntity toEntity(TournamentRequestDto dto);

  @Mapping(source = "organizer.id", target = "organizerId")
  TournamentResponseDto toDto(TournamentEntity entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "organizer", ignore = true)
  @Mapping(target = "district", ignore = true)
  @Mapping(target = "teams", ignore = true)
  void updateEntity(TournamentRequestDto dto, @MappingTarget TournamentEntity entity);

}
