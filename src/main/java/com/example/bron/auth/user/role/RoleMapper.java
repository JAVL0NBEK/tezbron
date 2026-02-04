package com.example.bron.auth.user.role;

import com.example.bron.auth.user.role.dto.RoleRequestDto;
import com.example.bron.auth.user.role.dto.RoleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
  RoleEntity toEntity(RoleRequestDto dto);

  RoleResponseDto toDto(RoleEntity entity);

}
