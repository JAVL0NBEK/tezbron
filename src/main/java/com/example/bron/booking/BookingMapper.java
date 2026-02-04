package com.example.bron.booking;

import com.example.bron.booking.dto.BookingRequestDto;
import com.example.bron.booking.dto.BookingResponseDto;
import com.example.bron.enums.StadiumDuration;
import com.example.bron.stadium.StadiumEntity;
import com.example.bron.stadium.dto.StadiumResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public interface BookingMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "stadium", ignore = true)
  @Mapping(target = "match", ignore = true)
  BookingEntity toEntity(BookingRequestDto dto);

  BookingResponseDto toDto(BookingEntity entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "user", ignore = true)
  void updateEntity(@MappingTarget BookingEntity entity, BookingRequestDto dto);

//  @AfterMapping
//  default void afterToDto(BookingEntity entity, @MappingTarget StadiumResponseDto dto) {
//    dto.setSlots(generateTimeSlots(entity.getDuration()));
//  }

//  default List<LocalDateTime> generateTimeSlots(StadiumDuration duration) {
//    if (duration == null) return List.of();
//
//    List<LocalDateTime> times = new ArrayList<>();
//    LocalDate today = LocalDate.now();
//    LocalDateTime start = today.atStartOfDay(); // 00:00
//    LocalDateTime end = today.plusDays(1).atStartOfDay(); // 24:00
//
//    int stepMinutes = duration.getMinutes();
//
//    while (!start.isAfter(end.minusMinutes(stepMinutes))) {
//      times.add(start);
//      start = start.plusMinutes(stepMinutes);
//    }
//
//    return times;
//  }
}
