package com.example.bron.match;

import com.example.bron.match.dto.MatchRequestDto;
import com.example.bron.match.dto.MatchResponseDto;
import com.example.bron.stadium.dto.AvailabilitySlotRequestDto;
import com.example.bron.stadium.dto.LocationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchMapper {

  ObjectMapper mapper = new ObjectMapper();

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "stadium", ignore = true)
  @Mapping(target = "organizer", ignore = true)
  MatchEntity toEntity(MatchRequestDto dto);


  MatchResponseDto toDto(MatchEntity entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(@MappingTarget MatchEntity entity, MatchRequestDto dto);

}
