package com.likelion.welcomekit.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class LetterResponseDTO {
    private String senderName;
    private String message;
}

