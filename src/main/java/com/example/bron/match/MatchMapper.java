package com.example.bron.match;

import com.example.bron.match.dto.MatchRequestDto;
import com.example.bron.match.dto.MatchResponseDto;
import com.example.bron.stadium.StadiumEntity;
import com.example.bron.stadium.dto.AvailabilitySlotRequestDto;
import com.example.bron.stadium.dto.LocationDto;
import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;
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
  @Mapping(target = "location", source = "location", qualifiedByName = "toJson")
  MatchEntity toEntity(MatchRequestDto dto);


  @Mapping(target = "location", source = "location", qualifiedByName = "fromJson")
  MatchResponseDto toDto(MatchEntity entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "location", source = "location", qualifiedByName = "toJson")
  void updateEntity(@MappingTarget MatchEntity entity, MatchRequestDto dto);

  // ========== HELPERS FOR JSON <-> DTO ==========

  @Named("toJson")
  default String toJson(Object value) {
    try {
      return value != null ? mapper.writeValueAsString(value) : null;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Named("fromJson")
  default LocationDto fromJson(String json) {
    try {
      return json != null ? mapper.readValue(json, LocationDto.class) : null;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Named("fromJsonList")
  default List<AvailabilitySlotRequestDto> fromJsonList(String json) {
    try {
      return json != null ? mapper.readValue(
          json,
          mapper.getTypeFactory().constructCollectionType(List.class, AvailabilitySlotRequestDto.class)
      ) : null;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
