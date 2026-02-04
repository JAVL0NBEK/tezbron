package com.example.bron.match.dto;

import com.example.bron.enums.Duration;
import com.example.bron.enums.MatchStatus;
import com.example.bron.stadium.dto.LocationDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchResponseDto {
  private Long id;
  private String title;
  private Long organizerId;
  private Long stadiumId;
  private LocalDateTime dateTime;
  private Duration duration;
  private Integer maxPlayers;
  private Integer currentPlayers;
  private Double pricePerPlayer;
  private MatchStatus status;
  private Object location;

  public MatchResponseDto(Long id, String title, Long organizerId, Long stadiumId,
      LocalDateTime dateTime, Duration duration, Integer maxPlayers, Integer currentPlayers,
      Double pricePerPlayer, MatchStatus status, Object location) {
    this.id = id;
    this.title = title;
    this.organizerId = organizerId;
    this.stadiumId = stadiumId;
    this.dateTime = dateTime;
    this.duration = duration;
    this.maxPlayers = maxPlayers;
    this.currentPlayers = currentPlayers;
    this.pricePerPlayer = pricePerPlayer;
    this.status = status;
    this.location = location;
  }

}
