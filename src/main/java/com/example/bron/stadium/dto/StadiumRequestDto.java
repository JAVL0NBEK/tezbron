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
public class StadiumRequestDto {
    private String name;
    private Long ownerId;
    private String description;
    private LocationDto location; // JSON string ({"lat":..., "lng":...})
    private StadiumType type;
    private Duration duration;
    private Integer capacity;
    private Double pricePerHour;
    private List<String> images;
    private List<AvailabilitySlotRequestDto> availabilitySlots; // JSON vaqt oraliqlari
    private Boolean isActive;
}
