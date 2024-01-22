package com.likelion.welcomekit.Domain.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LetterWelcomeRequestDTO {
    private Long targetId;
    private String message;
}
