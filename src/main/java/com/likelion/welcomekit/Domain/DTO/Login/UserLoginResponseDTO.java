package com.likelion.welcomekit.Domain.DTO.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponseDTO {
    private String jwtToken;
    private String role;
}
