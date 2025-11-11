package com.example.bron.auth.otp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpService {
  private final EskizSmsService eskizSmsService;
  private final RedisTemplate<String, String> redisTemplate;
  private final Random random = new Random();

  private static final int MAX_ATTEMPTS = 3;
  private static final Duration OTP_TTL = Duration.ofMinutes(5);
  private static final Duration ATTEMPT_TTL = Duration.ofMinutes(10);

  private String otpKey(String phone) {
    return "otp:" + phone;
  }

  private String attemptsKey(String phone) {
    return "otp_attempts:" + phone;
  }

  public void sendOtp(String phoneNumber) {
    String otp = generateOtp();
    saveOtp(phoneNumber, otp);
    resetAttempts(phoneNumber);

    eskizSmsService.sendSms(phoneNumber, "Siz TezBron Ilovasiga Ulanish Uchun Kod: " + otp);
    log.info("📩 OTP yuborildi: {} -> {}", phoneNumber, otp);
  }

  public void saveOtp(String phoneNumber, String otp) {
    redisTemplate.opsForValue().set(otpKey(phoneNumber), otp, OTP_TTL);
  }

  public String getOtp(String phoneNumber) {
    return redisTemplate.opsForValue().get(otpKey(phoneNumber));
  }

  public void clearOtp(String phoneNumber) {
    redisTemplate.delete(otpKey(phoneNumber));
  }

  public int getAttempts(String phoneNumber) {
    String attempts = redisTemplate.opsForValue().get(attemptsKey(phoneNumber));
    return attempts != null ? Integer.parseInt(attempts) : 0;
  }

  public void updateAttempts(String phoneNumber, int attempts) {
    redisTemplate.opsForValue().set(attemptsKey(phoneNumber), String.valueOf(attempts), ATTEMPT_TTL);
  }

  public void resetAttempts(String phoneNumber) {
    redisTemplate.delete(attemptsKey(phoneNumber));
  }

  public boolean verifyOtp(String phoneNumber, String otpCode) {
    String correctOtp = getOtp(phoneNumber);
    int attempts = getAttempts(phoneNumber);

    if (attempts >= MAX_ATTEMPTS) {
      log.warn("🚫 Telefon raqam bloklangan ({} urinishdan so‘ng): {}", MAX_ATTEMPTS, phoneNumber);
      return false;
    }

    if (correctOtp != null && correctOtp.equals(otpCode)) {
      clearOtp(phoneNumber);
      resetAttempts(phoneNumber);
      log.info("✅ To‘g‘ri OTP kiritildi: {}", phoneNumber);
      return true;
    } else {
      updateAttempts(phoneNumber, attempts + 1);
      log.warn("❌ Noto‘g‘ri OTP ({}/{}): {}", attempts + 1, MAX_ATTEMPTS, phoneNumber);
      return false;
    }
  }

  private String generateOtp() {
    int number = random.nextInt(900_000) + 100_000; // 6 xonali OTP
    return String.valueOf(number);
  }

}
