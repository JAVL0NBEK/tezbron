package com.example.bron.auth.user.permission;

import com.example.bron.auth.user.permission.dto.PermissionRequestDto;
import com.example.bron.auth.user.permission.dto.PermissionResponseDto;
import com.example.bron.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
  private final PermissionRepository repository;
  private final PermissionMapper mapper;

  @Override
  public PermissionResponseDto create(PermissionRequestDto dto) {
    if (repository.existsByName(dto.getName())) {
      throw new NotFoundException("Permission already exists", List.of(dto.getName()));
    }
    var entity = mapper.toEntity(dto);
    return mapper.toDto(repository.save(entity));
  }

  @Override
  public List<PermissionResponseDto> getPermissions() {
    var permissions = repository.findAll();
    return permissions.stream().map(mapper::toDto).toList();
  }

  @Override
  public void delete(Long id) {
   repository.deleteById(id);
  }
}
