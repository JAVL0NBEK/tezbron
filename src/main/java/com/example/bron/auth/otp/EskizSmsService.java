package com.example.bron.auth.otp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EskizSmsService {

  private final RestTemplate restTemplate = new RestTemplate();

  private static final String BASE_URL = "https://notify.eskiz.uz/api";
  private static final String EMAIL = "yangilari.uz@mail.ru";
  private static final String PASSWORD = "bERxD5aYgXVkwjrLLECEdLgHSYgLRaAtC3uqRKvu";

  @Cacheable(value = "eskizTokenCache", key = "'token'")
  public String getToken() {
    log.info("Eskiz token cache’da topilmadi, yangisini olayapman...");
    return fetchNewToken();
  }

  @CacheEvict(value = "eskizTokenCache", key = "'token'")
  public void refreshToken() {
    log.info("Eskiz tokenni yangilayapman...");
    fetchNewToken();
  }

  private String fetchNewToken() {
    Map<String, String> payload = Map.of(
        "email", EMAIL,
        "password", PASSWORD
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

    var response = restTemplate.postForEntity(BASE_URL + "/auth/login", request, Map.class);
    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
      Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
      return (String) data.get("token");
    }

    throw new RuntimeException("Eskiz token olishda xatolik!");
  }

  public void sendSms(String phoneNumber, String message) {
    String token = getToken();
    Map<String, String> payload = Map.of(
        "mobile_phone", phoneNumber,
        "message", message,
        "from", "4546"
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(token);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/message/sms/send", request, String.class);

    if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
      refreshToken();
      sendSms(phoneNumber, message);
    } else if (!response.getStatusCode().is2xxSuccessful()) {
      log.error("❌ SMS yuborishda xatolik: {}", response.getBody());
    } else {
      log.info("✅ SMS yuborildi -> {}", phoneNumber);
    }
  }
}

