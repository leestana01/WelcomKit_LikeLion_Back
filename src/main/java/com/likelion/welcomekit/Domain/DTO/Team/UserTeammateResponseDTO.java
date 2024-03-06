package com.likelion.welcomekit.Domain.DTO.Team;

import com.likelion.welcomekit.Domain.Types;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserTeammateResponseDTO {

    private String name;
    private String imageUrl;
    private String department;
    private Types.PartType part;

    private Types.UserType userType;
    private boolean isTeamLeader;
    private String teamMessage;
    private String teamLeaderMessage;
}
