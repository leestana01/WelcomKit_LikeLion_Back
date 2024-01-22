package com.likelion.welcomekit.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class LetterRequestDTO {
    private Long targetId;
    private String message;
    private boolean isWelcome;
}
