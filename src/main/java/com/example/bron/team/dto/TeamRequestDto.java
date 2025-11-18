package com.example.bron.team.dto;

import com.example.bron.enums.StadiumType;
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
public class TeamRequestDto {

  private String name;

  private Long captainId;

  private StadiumType sportType;

  private List<Long> memberIds;

  private String description;

  private LocalDateTime createdAt;
}
