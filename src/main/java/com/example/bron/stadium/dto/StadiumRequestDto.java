package com.example.bron.stadium.dto;

import com.example.bron.common.FileResponseDto;
import com.example.bron.enums.Duration;
import com.example.bron.enums.StadiumDuration;
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
    private Long regionId;
    private Long districtId;
    private String description;
    private LocationDto location; // JSON string ({"lat":..., "lng":...})
    private StadiumType type;
    private StadiumDuration duration;
    private Integer capacity;
    private Double pricePerHour;
    private List<FileResponseDto> images;
    private Boolean isActive;
}
