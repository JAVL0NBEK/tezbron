package com.example.bron.auth.user.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import lombok.Data;

@Data
public class AssignRoleRequestDto {
  @NotEmpty
  private Set<Long> roleIds;

}
