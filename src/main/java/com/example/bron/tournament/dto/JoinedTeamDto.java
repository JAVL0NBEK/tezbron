package com.example.bron.tournament.dto;

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
public class JoinedTeamDto {
  private Long id;
  private String name;
  private SportType sportType;
  private String description;
  private LocalDateTime createdAt;
  private List<Long> memberIds;
}