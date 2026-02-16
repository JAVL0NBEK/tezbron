package com.example.bron.team.dto;

import com.example.bron.enums.SportType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestDto {

  private String name;

  private SportType sportType;

  private Long maxMembers;

  private String description;

  private LocalDateTime createdAt;
}
