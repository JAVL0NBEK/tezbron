package com.example.bron.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StadiumDuration {
  SIXTY(60),
  NINETY(90),
  ONE_HUNDRED_TWENTY(120);

  private final int minutes;
}
