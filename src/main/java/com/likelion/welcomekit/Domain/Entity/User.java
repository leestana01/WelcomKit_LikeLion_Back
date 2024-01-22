package com.likelion.welcomekit.Domain.Entity;

import com.likelion.welcomekit.Domain.Types;
import jakarta.persistence.*;
import lombok.*;

@Getter @Builder
@Entity
@Table(indexes = @Index(name = "idx__teamId", columnList = "teamId"))
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    private String department;
    @Enumerated(EnumType.STRING)
    private Types.PartType part;

    @Enumerated(EnumType.STRING)
    private Types.UserType userType;
    private boolean isTeamLeader;
    private String teamMessage;

    private Long teamId;

    @OneToOne(fetch = FetchType.LAZY)
    private User manitoTo;
    @OneToOne(fetch = FetchType.LAZY)
    private User manitoFrom;


    public void setTeamMessage(String teamMessage) {
        this.teamMessage = teamMessage;
    }

    public void setTeamLeader(boolean teamLeader) {
        isTeamLeader = teamLeader;
    }

    public void setManitoTo(User manitoTo) {
        this.manitoTo = manitoTo;
    }

    public void setManitoFrom(User manitoFrom) {
        this.manitoFrom = manitoFrom;
    }
}
