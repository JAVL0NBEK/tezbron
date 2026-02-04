package com.example.bron.auth.user.permission;

import com.example.bron.auth.user.permission.dto.PermissionRequestDto;
import com.example.bron.auth.user.permission.dto.PermissionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper {

  PermissionEntity toEntity(PermissionRequestDto dto);

  PermissionResponseDto toDto(PermissionEntity entity);

}
