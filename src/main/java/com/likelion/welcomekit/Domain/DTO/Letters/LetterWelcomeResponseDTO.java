package com.likelion.welcomekit.Domain.DTO.Letters;

import com.likelion.welcomekit.Domain.Types;
import lombok.*;

@Getter
@AllArgsConstructor
public class LetterWelcomeResponseDTO {
    private String senderName;
    private Types.PartType part;
    private String message;
    private String profileUrl;
    private String profileMiniUrl;
}

