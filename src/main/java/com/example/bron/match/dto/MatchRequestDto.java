package com.example.bron.match.dto;

import com.example.bron.enums.Duration;
import com.example.bron.enums.MatchStatus;
import com.example.bron.enums.SportType;
import com.example.bron.stadium.dto.LocationDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchRequestDto {

  private String title;
  private Long organizerId;
  private Long stadiumId;
  private LocalDateTime dateTime;
  private Duration duration;
  private Integer maxPlayers;
  private Integer currentPlayers;
  private Double pricePerPlayer;
  private MatchStatus status;
  private LocationDto location;
  private SportType sportType;
}
