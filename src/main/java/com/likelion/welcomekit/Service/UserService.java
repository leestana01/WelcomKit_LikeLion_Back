package com.likelion.welcomekit.Service;

import com.likelion.welcomekit.Domain.DTO.UserDTO;
import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserDTO userDTO){
        // TODO: 팀의 유일한 Manager이면 팀장 처리
        userRepository.findByName(userDTO.getName())
                .ifPresent(found -> {
                    throw new EntityExistsException(userDTO.getName());
                });

        User newUser = User.builder()
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .department(userDTO.getDepartment())
                .part(userDTO.getPart())
                .isManager(userDTO.isManager())
                .isTeamLeader(false)
                .teamMessage("")
                .teamId(0L)
                .manitoTo(null)
                .manitoFrom(null)
                .build();

    }

    public void updateTeamMessage(Long managerId, String message){
    }

    public void updateTeamLeader(Long managerId){
    }
}
