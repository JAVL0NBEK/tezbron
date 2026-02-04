package com.example.bron.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
  private boolean success;
  private String message;
  private T data;

  public static <T> BaseResponse<T> ok(T data) {
    return BaseResponse.<T>builder()
        .success(true)
        .message("Success")
        .data(data)
        .build();
  }

  public static <T> BaseResponse<T> created(T data) {
    return BaseResponse.<T>builder()
        .success(true)
        .message("Resource created successfully")
        .data(data)
        .build();
  }

  public static <T> BaseResponse<T> noContent(String message) {
    return BaseResponse.<T>builder()
        .success(true)
        .message(message)
        .build();
  }

  public static <T> BaseResponse<T> error(String message) {
    return BaseResponse.<T>builder()
        .success(false)
        .message(message)
        .build();
  }
}
