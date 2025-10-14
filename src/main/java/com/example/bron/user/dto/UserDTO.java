package com.example.bron.user.dto;

import com.example.bron.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String profileImageUrl;
    private String location;
    private LocalDateTime createdAt;
    private Role role;
}
