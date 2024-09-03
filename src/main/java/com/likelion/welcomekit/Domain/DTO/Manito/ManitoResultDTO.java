package com.likelion.welcomekit.Domain.DTO.Manito;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ManitoResultDTO {
    private String name;
    private String manitoTo;
    private String manitoFrom;
    private String selectedManito;
    private boolean isGuessRight;
}
