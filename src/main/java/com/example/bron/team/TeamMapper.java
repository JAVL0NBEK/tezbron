package com.example.bron.team;

import com.example.bron.team.dto.TeamRequestDto;
import com.example.bron.team.dto.TeamResponseDto;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamMapper {

  @Mapping(target = "id", ignore = true)
  TeamEntity toEntity(TeamRequestDto dto);

  @Mapping(target = "memberIds", source = "members")
  TeamResponseDto toDto(TeamEntity entity);

  // shu method avtomatik ishlaydi
  default List<Long> mapMembers(List<TeamMemberEntity> members) {
    if (members == null) return List.of();

    return members.stream()
        .map(TeamMemberEntity::getId)
        .toList();
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(@MappingTarget TeamEntity entity, TeamRequestDto dto);

}
