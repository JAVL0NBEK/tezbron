package com.example.bron.notification;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

  private final FirebaseMessaging firebaseMessaging;

  public void sendToUser(Long userId, String title, String body,
      Map<String, String> data, DeviceTokenRepository deviceTokenRepository) {
    List<String> tokens = deviceTokenRepository.findTokensByUserId(userId);
    if (tokens.isEmpty()) {
      log.debug("No device tokens found for user {}", userId);
      return;
    }
    sendToTokens(tokens, title, body, data);
  }

  public void sendToUsers(List<Long> userIds, String title, String body,
      Map<String, String> data, DeviceTokenRepository deviceTokenRepository) {
    List<String> tokens = deviceTokenRepository.findTokensByUserIds(userIds);
    if (tokens.isEmpty()) {
      log.debug("No device tokens found for users {}", userIds);
      return;
    }
    sendToTokens(tokens, title, body, data);
  }

  public void sendToAll(String title, String body,
      Map<String, String> data, DeviceTokenRepository deviceTokenRepository) {
    List<String> tokens = deviceTokenRepository.findAllTokens();
    if (tokens.isEmpty()) {
      log.debug("No device tokens found");
      return;
    }
    sendToTokens(tokens, title, body, data);
  }

  public void sendToTopic(String topic, String title, String body, Map<String, String> data) {
    Message.Builder builder = Message.builder()
        .setTopic(topic)
        .setNotification(Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build());
    if (data != null && !data.isEmpty()) {
      builder.putAllData(data);
    }
    try {
      String response = firebaseMessaging.send(builder.build());
      log.info("Sent topic message: {}", response);
    } catch (FirebaseMessagingException e) {
      log.error("Failed to send topic message: {}", e.getMessage());
    }
  }

  private void sendToTokens(List<String> tokens, String title, String body,
      Map<String, String> data) {
    Notification notification = Notification.builder()
        .setTitle(title)
        .setBody(body)
        .build();

    Map<String, String> safeData = data != null ? data : Collections.emptyMap();

    // FCM multicast limit - 500 ta token
    for (int i = 0; i < tokens.size(); i += 500) {
      List<String> batch = tokens.subList(i, Math.min(i + 500, tokens.size()));
      MulticastMessage message = MulticastMessage.builder()
          .addAllTokens(batch)
          .setNotification(notification)
          .putAllData(safeData)
          .build();
      try {
        BatchResponse response = firebaseMessaging.sendEachForMulticast(message);
        log.info("Sent {}/{} messages successfully",
            response.getSuccessCount(), batch.size());
      } catch (FirebaseMessagingException e) {
        log.error("Failed to send multicast message: {}", e.getMessage());
      }
    }
  }
}
