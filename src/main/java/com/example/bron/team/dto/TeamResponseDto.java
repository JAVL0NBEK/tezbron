package com.example.bron.team.dto;

import com.example.bron.enums.SportType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponseDto {
  private Long id;

  private String name;

  private SportType sportType;

  private List<Long> memberIds;

  private String description;

  private LocalDateTime createdAt;
}
