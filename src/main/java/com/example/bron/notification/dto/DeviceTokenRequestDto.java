package com.example.bron.notification.dto;

import com.example.bron.notification.DeviceTokenEntity.DeviceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceTokenRequestDto {
  private Long userId;
  private String token;
  private DeviceType deviceType;
}
