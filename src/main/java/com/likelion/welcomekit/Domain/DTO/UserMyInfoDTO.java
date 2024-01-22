package com.likelion.welcomekit.Domain.DTO;

import com.likelion.welcomekit.Domain.Types;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserMyInfoDTO {
    private String name;
    private Types.UserType userType;
}
