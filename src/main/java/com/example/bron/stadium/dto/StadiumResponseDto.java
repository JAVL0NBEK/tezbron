package com.example.bron.stadium.dto;

import com.example.bron.enums.Duration;
import com.example.bron.enums.StadiumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadiumResponseDto {
    private Long id;
    private String name;
    private String ownerName;
    private String description;
    private LocationDto location;
    private StadiumType type;
    private Duration duration;
    private Integer capacity;
    private Double pricePerHour;
    private List<String> images;
    private List<AvailabilitySlotRequestDto> availabilitySlots;
    private Boolean isActive;
}
