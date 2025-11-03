package com.example.bron.stadium;

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
public interface StadiumMapper {

  ObjectMapper mapper = new ObjectMapper();

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "matches", ignore = true)
  @Mapping(target = "location", source = "location", qualifiedByName = "toJson")
  @Mapping(target = "availabilitySlots", source = "availabilitySlots", qualifiedByName = "toJson")
  StadiumEntity toEntity(StadiumRequestDto dto);


  @Mapping(target = "ownerName", ignore = true)
  @Mapping(target = "location", source = "location", qualifiedByName = "fromJson")
  @Mapping(target = "availabilitySlots", source = "availabilitySlots", qualifiedByName = "fromJsonList")
  StadiumResponseDto toDto(StadiumEntity entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "matches", ignore = true)
  @Mapping(target = "location", source = "location", qualifiedByName = "toJson")
  @Mapping(target = "availabilitySlots", source = "availabilitySlots", qualifiedByName = "toJson")
  void updateEntity(@MappingTarget StadiumEntity entity, StadiumRequestDto dto);

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
