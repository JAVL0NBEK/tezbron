package com.example.bron.stadium;

import com.example.bron.enums.StadiumDuration;
import com.example.bron.enums.StadiumType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StadiumFilterParams {
  private Long id;
  private String name;
  private Long ownerId;
  private String ownerName;
  private String description;
  private StadiumType type;
  private StadiumDuration duration;
  private Integer capacity;
  private Double pricePerHour;
  private Boolean isActive;

  @Schema(hidden = true)
  public Boolean getStadiumTypeIsNull() {
    return this.type == null;
  }

  @Schema(hidden = true)
  public Boolean getStadiumDurationIsNull() {
    return this.duration == null;
  }

}
