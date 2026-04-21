package com.example.bron.location.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DistrictResponseDto {
  private Long id;
  private String name;
  private String shortName;
  private Long provinceId;
  private Long regionId;

  public DistrictResponseDto(Long id, String name, String shortName) {
    this.id = id;
    this.name = name;
    this.shortName = shortName;
  }
}
