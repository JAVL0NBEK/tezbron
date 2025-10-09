package com.example.bron.auth.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Integer expiresIn;
    private Integer refreshExpiresIn;
    private String code;
    private Long positionId;
    private String login;
    private Long languageId;
    private Boolean status;
    private Long userId;

    public LoginResponseDto(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }
}
