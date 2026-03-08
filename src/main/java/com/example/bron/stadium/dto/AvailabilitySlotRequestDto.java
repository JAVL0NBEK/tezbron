package com.example.bron.stadium.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilitySlotRequestDto {
  private LocalDateTime start;
  private LocalDateTime end;
  private boolean available;
}
