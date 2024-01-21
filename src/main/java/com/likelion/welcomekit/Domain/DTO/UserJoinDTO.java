package com.likelion.welcomekit.Domain.DTO;

import com.likelion.welcomekit.Domain.PartType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserJoinDTO {

    private String name;
    private String password;

    private String department;
    private PartType part;
    private boolean isManager;
    private Long teamId;
}
