package com.likelion.welcomekit.Domain.DTO.Info;

import com.likelion.welcomekit.Domain.Types;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserMyPageForManagerRequestDTO {
    private String department;
    private Long teamId;
    private Types.PartType part;

    private String teamMessage;
    private String teamLeaderMessage;
}
