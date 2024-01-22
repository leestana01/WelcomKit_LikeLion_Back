package com.likelion.welcomekit.Domain.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LetterRequestDTO {
    private Long targetId;
    private String message;
    private Boolean isWelcome;
}
