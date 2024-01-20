package com.likelion.welcomekit.Domain.DTO;

import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Domain.PartType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserDTO{

    private String name;
    private String password;

    private String department;
    private PartType part;
    private boolean isManager;
    private Long teamId;
}
