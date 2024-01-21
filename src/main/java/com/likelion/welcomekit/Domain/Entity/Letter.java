package com.likelion.welcomekit.Domain.Entity;

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

    @OneToOne(fetch = FetchType.LAZY)
    private User target;
    @OneToOne(fetch = FetchType.LAZY)
    private User sender;
    private String message;
    private boolean isWelcome;
}
