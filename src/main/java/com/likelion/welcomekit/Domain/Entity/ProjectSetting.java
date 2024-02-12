package com.likelion.welcomekit.Domain.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
public class ProjectSetting {
    @Id
    private Long id = 0L;

    private boolean isManitoActive = false;
    private boolean isManitoFinished = false;
}