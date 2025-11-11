package com.example.bron.auth.otp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "eskiz")
public class EskizProperties {
  private String baseUrl;
  private String email;
  private String password;
  private String sender;
  private int tokenExpireHours;
}
