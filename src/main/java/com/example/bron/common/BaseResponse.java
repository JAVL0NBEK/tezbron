package com.example.bron.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
  private boolean success;
  private String message;
  private String code;
  private List<Object> details;
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

  public static <T> BaseResponse<T> error(String code, String message, List<Object> details) {
    return BaseResponse.<T>builder()
        .success(false)
        .code(code)
        .message(message)
        .details(details == null || details.isEmpty() ? null : details)
        .build();
  }
}
