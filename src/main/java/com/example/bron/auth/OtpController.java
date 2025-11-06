package com.example.bron.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {

  private final OtpService otpService;

  /**
   * OTP yuborish
   */
  @PostMapping("/send")
  public ResponseEntity<String> sendOtp(@RequestParam String phoneNumber) {
    otpService.sendOtp(phoneNumber);
    return ResponseEntity.ok("OTP yuborildi!");
  }

  /**
   * OTP tekshirish
   */
  @PostMapping("/verify")
  public ResponseEntity<String> verifyOtp(
      @RequestParam String phoneNumber,
      @RequestParam String otpCode
  ) {
    boolean verified = otpService.verifyOtp(phoneNumber, otpCode);
    if (verified) {
      return ResponseEntity.ok("OTP to'g'ri ✅");
    } else {
      return ResponseEntity.badRequest().body("OTP noto'g'ri yoki telefon raqam bloklangan ❌");
    }
  }
}
