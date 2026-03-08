package com.example.bron.stadium.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UpdateTimeDto {
  private LocalDateTime openTime;
  private LocalDateTime closeTime;

}
