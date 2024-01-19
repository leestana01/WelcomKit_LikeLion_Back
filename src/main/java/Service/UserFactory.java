package Service;

import Domain.DTO.UserDTO;
import Domain.Entity.BabyLion;
import Domain.Entity.Manager;
import Domain.Entity.User;

public class UserFactory {
    public static User createUser(UserDTO userDTO){
        if (userDTO.isManager()){
            return Manager.builder()
                    .name(userDTO.getName())
                    .password(userDTO.getPassword())
                    .department(userDTO.getDepartment())
                    .part(userDTO.getPart())
                    .isManager(userDTO.isManager())
                    .teamId(userDTO.getTeamId())
                    .manitoTo(userDTO.getManitoTo())
                    .manitoFrom(userDTO.getManitoFrom())
                    .teamMessage(null)
                    .isTeamLeader(false)
                    .build();
        } else {
            return BabyLion.builder()
                    .name(userDTO.getName())
                    .password(userDTO.getPassword())
                    .department(userDTO.getDepartment())
                    .part(userDTO.getPart())
                    .isManager(userDTO.isManager())
                    .teamId(userDTO.getTeamId())
                    .manitoTo(userDTO.getManitoTo())
                    .manitoFrom(userDTO.getManitoFrom())
                    .build();
        }
    }
}
