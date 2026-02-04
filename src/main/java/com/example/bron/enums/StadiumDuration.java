package com.example.bron.enums;

public enum StadiumDuration {
  SIXTY(60),
  NINETY(90),
  ONE_HUNDRED_TWENTY(120);

  private final int minutes;

  StadiumDuration(int minutes) {
    this.minutes = minutes;
  }

  public int getMinutes() {
    return minutes;
  }
}
