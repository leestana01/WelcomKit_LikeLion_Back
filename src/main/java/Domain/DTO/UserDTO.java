package Domain.DTO;

import Domain.Entity.User;
import Domain.PartType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserDTO implements User {

    private String name;
    private String password;

    private String department;
    private PartType part;
    private boolean isManager;
    private Long teamId;
    private User manitoTo;
    private User manitoFrom;
}
