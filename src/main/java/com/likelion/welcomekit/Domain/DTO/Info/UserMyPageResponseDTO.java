package com.likelion.welcomekit.Domain.DTO.Info;

import com.likelion.welcomekit.Domain.Types;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserMyPageResponseDTO {
    private String name;
    private Long teamId;
    private boolean isTeamLeader;
    private Types.PartType part;
    private String department;

    private String teamMessage;
    private String teamLeaderMessage;

    private String profileUrl;
    private String profileMiniUrl;
}
