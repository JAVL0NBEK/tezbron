package com.example.bron.team;

import com.example.bron.team.dto.TeamRequestDto;
import com.example.bron.team.dto.TeamResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "captain", ignore = true)
  TeamEntity toEntity(TeamRequestDto dto);

  TeamResponseDto toDto(TeamEntity entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "captain", ignore = true)
  void updateEntity(@MappingTarget TeamEntity entity, TeamRequestDto dto);

}
