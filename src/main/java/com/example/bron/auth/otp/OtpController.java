package com.example.bron.auth.otp;

import com.example.bron.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController implements OtpApi{

  private final OtpService otpService;

  @Override
  public ResponseEntity<BaseResponse<Void>> sendOtp(String phoneNumber) {
    otpService.sendOtp(phoneNumber);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(BaseResponse.noContent("OTP yuborildi"));
  }

  /**
   * OTP tekshirish
   */
  @Override
  public ResponseEntity<BaseResponse<Void>> verifyOtp(String phoneNumber, String otpCode) {
    boolean verified = otpService.verifyOtp(phoneNumber, otpCode);

    if (verified) {
      return ResponseEntity.ok(BaseResponse.noContent("OTP to‘g‘ri"));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(BaseResponse.error("OTP noto‘g‘ri yoki telefon raqam bloklangan!"));
    }
  }
}
