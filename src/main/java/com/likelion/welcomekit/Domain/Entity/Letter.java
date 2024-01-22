package com.likelion.welcomekit.Domain.Entity;

import com.likelion.welcomekit.Domain.DTO.LetterResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long targetId;
    private Long senderId;
    private String message;
    private boolean isWelcome;
}
