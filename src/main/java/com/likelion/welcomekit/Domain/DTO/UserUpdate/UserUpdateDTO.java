package com.likelion.welcomekit.Domain.DTO.UserUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private Long id;
    private String name;
    private Long teamId;
    private boolean isTeamLeader;
}
