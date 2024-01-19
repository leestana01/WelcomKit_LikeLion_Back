package Domain.Entity;

import Domain.PartType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
@Entity
@AllArgsConstructor
public class BabyLion implements User{
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
}
