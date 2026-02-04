package com.example.bron.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegionResponseDto {
  private Long id;
  private String name;
  private String shortName;
}
