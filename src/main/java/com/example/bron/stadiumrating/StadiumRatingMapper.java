package com.example.bron.stadiumrating;

import com.example.bron.stadiumrating.dto.StadiumRatingResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StadiumRatingMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "userFullName", source = "user.username")
  @Mapping(target = "stadiumName", source = "stadium.name")
  @Mapping(target = "stadiumId", source = "stadium.id")
  StadiumRatingResponseDto toDto(StadiumRatingEntity entity);

}
