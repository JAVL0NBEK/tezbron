package com.example.bron.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelBookingRequestDto {

  @NotBlank
  @Size(max = 500)
  private String reason;
}
