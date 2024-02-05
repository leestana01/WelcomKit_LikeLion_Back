package com.likelion.welcomekit.Domain.DTO.Info;

import com.likelion.welcomekit.Domain.Types;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
// 운영진의 유저 정보 조회 시 DTO
public class UserInfoResponseDTO {
    private Long id;
    private String name;
    private String department;
    private Long teamId;
    private Types.PartType part;

    private Types.UserType userType;
    private boolean isTeamLeader;
    private String teamMessage;

    private boolean isMessageWritten;
}
