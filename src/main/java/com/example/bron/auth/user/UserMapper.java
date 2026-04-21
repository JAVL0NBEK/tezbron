package com.example.bron.auth.user;

import com.example.bron.auth.dto.LoginResponseDto;
import com.example.bron.auth.user.role.RoleEntity;
import com.example.bron.stadium.dto.LocationDto;
import com.example.bron.auth.user.dto.UserDTO;
import com.example.bron.auth.user.dto.UserRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
  ObjectMapper mapper = new ObjectMapper();

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "location", source = "location", qualifiedByName = "toJson")
  UserEntity toEntity(UserRequestDto dto);

//  @Mapping(target = "location", source = "location", qualifiedByName = "fromJson")
//  @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()))")
//  @Mapping(target = "permissions", expression = "java(user.getRoles().stream().flatMap(r -> r.getPermissions().stream()).map(p -> p.getName()).collect(Collectors.toSet()))")
  UserDTO toDto(UserEntity entity);

  @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToNames")
  LoginResponseDto toLoginDto(UserEntity entity);

  @Named("rolesToNames")
  default Set<String> rolesToNames(Set<RoleEntity> roles) {
    return roles == null ? Set.of() : roles.stream().map(RoleEntity::getName).collect(Collectors.toSet());
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "location", source = "location", qualifiedByName = "toJson")
  void updateEntity(@MappingTarget UserEntity entity, UserRequestDto dto);

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

}
