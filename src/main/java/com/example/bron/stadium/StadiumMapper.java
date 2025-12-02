package com.example.bron.stadium;

import com.example.bron.enums.StadiumDuration;
import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StadiumMapper {

  ObjectMapper mapper = new ObjectMapper();

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "matches", ignore = true)
  StadiumEntity toEntity(StadiumRequestDto dto);


  @Mapping(target = "ownerId", source = "owner.id")
  @Mapping(target = "ownerName", source = "owner.username")
  StadiumResponseDto toDto(StadiumEntity entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "matches", ignore = true)
  void updateEntity(@MappingTarget StadiumEntity entity, StadiumRequestDto dto);

  default List<LocalDateTime> generateTimeSlots(StadiumDuration duration) {
    if (duration == null) return List.of();

    List<LocalDateTime> times = new ArrayList<>();
    LocalDate today = LocalDate.now();
    LocalDateTime start = today.atStartOfDay(); // 00:00
    LocalDateTime end = today.plusDays(1).atStartOfDay(); // 24:00

    int stepMinutes = duration.getMinutes();

    while (!start.isAfter(end.minusMinutes(stepMinutes))) {
      times.add(start);
      start = start.plusMinutes(stepMinutes);
    }

    return times;
  }

}
