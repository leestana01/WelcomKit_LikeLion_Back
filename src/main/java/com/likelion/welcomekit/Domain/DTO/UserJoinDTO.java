package com.likelion.welcomekit.Domain.DTO;

import com.likelion.welcomekit.Domain.Types;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.likelion.welcomekit.Domain.Types.UserType.USER;

@Getter @Setter
@NoArgsConstructor
public class UserJoinDTO {
    private String name;
    private String password;

    private String department;
    private Types.PartType part;
    private Types.UserType userType = USER;
    private Long teamId;
}