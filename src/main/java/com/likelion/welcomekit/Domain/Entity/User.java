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

    private String profileMiniUrl;
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private Types.UserType userType;
    private boolean isTeamLeader;
    private String teamMessage;
    private String teamLeaderMessage;
    private String currier; // 사용하지 않음. 기존 DB 처리 문제로 보존.

    private Long teamId;

    private boolean isManitoDisabled;
    @OneToOne(fetch = FetchType.LAZY)
    private User manitoTo;
    @OneToOne(fetch = FetchType.LAZY)
    private User manitoFrom;

    // Setter 안 쓴 이유 : 보안 -> 아기사자가 의도치 않은 우회 공격 수행 시, 대응하기 위함
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setPart(Types.PartType part) {
        this.part = part;
    }
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    public void setTeamMessage(String teamMessage) {
        this.teamMessage = teamMessage;
    }
    public void setTeamLeaderMessage(String teamLeaderMessage) {
        this.teamLeaderMessage = teamLeaderMessage;
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
    public void setProfileMiniUrl(String profileMiniUrl) {
        this.profileMiniUrl = profileMiniUrl;
    }
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
    public void setPassword(String encodedPassword){
        this.password = encodedPassword;
    }
}
