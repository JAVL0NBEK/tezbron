package com.example.bron.stadium.dto;

import com.example.bron.common.FileResponseDto;
import com.example.bron.enums.StadiumDuration;
import com.example.bron.enums.StadiumType;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StadiumResponseDto {
    private Long id;
    private String name;
    private Long ownerId;
    private String ownerName;
    private String description;
    private Object location;
    private StadiumType type;
    private StadiumDuration duration;
    private Integer capacity;
    private Double pricePerHour;
    private Boolean isActive;
    private String districtName;
    private String regionName;
    private Boolean isFavorite;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;


  public StadiumResponseDto(Long id, String name, Long ownerId, String ownerName,
      String description,
      Object location, StadiumType type, StadiumDuration duration, Integer capacity,
      Double pricePerHour, Boolean isActive, String districtName, String regionName, Boolean isFavorite,LocalDateTime openTime, LocalDateTime closeTime) {
    this.id = id;
    this.name = name;
    this.ownerId = ownerId;
    this.ownerName = ownerName;
    this.description = description;
    this.location = location;
    this.type = type;
    this.duration = duration;
    this.capacity = capacity;
    this.pricePerHour = pricePerHour;
    this.isActive = isActive;
    this.districtName = districtName;
    this.regionName = regionName;
    this.isFavorite = isFavorite;
    this.openTime = openTime;
    this.closeTime = closeTime;
  }

  private List<AvailabilitySlotRequestDto> slots;

}
