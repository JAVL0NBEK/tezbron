package com.example.bron.coach;

import com.example.bron.coach.dto.CoachRequestDto;
import com.example.bron.coach.dto.CoachResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoachMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  CoachEntity toEntity(CoachRequestDto dto);

  CoachResponseDto toDto(CoachEntity entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "user", ignore = true)
  void updateEntity(@MappingTarget CoachEntity entity, CoachRequestDto dto);

}
