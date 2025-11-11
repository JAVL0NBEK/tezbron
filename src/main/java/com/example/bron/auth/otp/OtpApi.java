package com.example.bron.auth.otp;

import com.example.bron.common.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/otp")
@Tag(name = "OTP Management", description = "Endpoints for sending and verifying OTP codes")
public interface OtpApi {
  /**
   * OTP yuborish
   */
  @PostMapping("/send")
  ResponseEntity<BaseResponse<Void>> sendOtp(@RequestParam String phoneNumber);

  /**
   * OTP tekshirish
   */
  @PostMapping("/verify")
  ResponseEntity<BaseResponse<Void>> verifyOtp(
      @RequestParam String phoneNumber,
      @RequestParam String otpCode
  );
}
