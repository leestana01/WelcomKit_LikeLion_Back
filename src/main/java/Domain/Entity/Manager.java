package Domain.Entity;

import Domain.PartType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter @Builder
@Entity
@AllArgsConstructor
public class Manager implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    private String department;
    private PartType part;
    private boolean isManager;
    private Long teamId;
    private User manitoTo;
    private User manitoFrom;

    private String teamMessage;
    private boolean isTeamLeader;

    public void setTeamMassage(String teamMassage) {
        this.teamMessage = teamMassage;
    }

    public void setTeamLeader(boolean teamLeader) {
        isTeamLeader = teamLeader;
    }
}
