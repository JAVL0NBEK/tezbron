package com.example.bron.booking;

import com.example.bron.booking.dto.BookingRequestDto;
import com.example.bron.booking.dto.BookingResponseDto;
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

}
