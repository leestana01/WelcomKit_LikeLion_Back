package com.likelion.welcomekit.Domain.DTO.Manito;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ManitoResponseDTO {
    String manitoTo;
    String manitoFrom;
    String selectedManito;
    int isGuessRight;
}
