package com.likelion.welcomekit.Domain.DTO.Info;

import com.likelion.welcomekit.Domain.Types;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 우측 바 상단의 간략한 프로필 영역
public class UserMyInfoResponseDTO {
    private String name;
    private Types.UserType userType;
    private String profileUrl;
    private String profileMiniUrl;
}
