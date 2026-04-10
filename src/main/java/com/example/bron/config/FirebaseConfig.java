package com.example.bron.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
public class FirebaseConfig {

  @Value("${firebase.service-account-path}")
  private String serviceAccountPath;

  @PostConstruct
  public void init() {
    try {
      if (FirebaseApp.getApps().isEmpty()) {
        InputStream serviceAccount = new ClassPathResource(serviceAccountPath).getInputStream();
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();
        FirebaseApp.initializeApp(options);
        log.info("Firebase initialized successfully");
      }
    } catch (IOException e) {
      log.warn("Firebase service account not found: {}. Push notifications will not work.", e.getMessage());
    }
  }

  @Bean
  public FirebaseMessaging firebaseMessaging() {
    return FirebaseMessaging.getInstance();
  }
}
