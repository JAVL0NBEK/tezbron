package com.example.bron.stadiumrating;

import com.example.bron.stadiumrating.dto.StadiumRatingResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StadiumRatingMapper {

  StadiumRatingResponseDto toDto(StadiumRatingEntity entity);

}
