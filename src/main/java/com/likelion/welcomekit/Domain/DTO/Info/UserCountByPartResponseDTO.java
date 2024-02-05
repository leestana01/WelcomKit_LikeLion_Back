package com.likelion.welcomekit.Domain.DTO.Info;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserCountByPartResponseDTO {
    private Long managers;
    private Long babyLions;

    private Long back;
    private Long front;
    private Long design;
    private Long dev;
}
