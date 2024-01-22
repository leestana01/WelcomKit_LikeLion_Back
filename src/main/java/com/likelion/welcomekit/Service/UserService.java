package com.likelion.welcomekit.Service;

import com.likelion.welcomekit.Domain.DTO.UserJoinDTO;
import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Domain.Types;
import com.likelion.welcomekit.Exception.EntityDuplicatedException;
import com.likelion.welcomekit.Exception.EntityNotManagerException;
import com.likelion.welcomekit.Exception.InvalidDBException;
import com.likelion.welcomekit.Exception.InvalidPasswordException;
import com.likelion.welcomekit.Repository.UserRepository;
import com.likelion.welcomekit.Utils.JwtTokenProvider;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public void createUser(UserJoinDTO userJoinDTO){
        // TODO: 팀의 유일한 Manager이면 팀장 처리
        userRepository.findByName(userJoinDTO.getName())
                .ifPresent(found -> {
                    throw new EntityExistsException(userJoinDTO.getName());
                });

        User newUser = User.builder()
                .name(userJoinDTO.getName())
                .password(encoder.encode(userJoinDTO.getPassword()))
                .department(userJoinDTO.getDepartment())
                .part(userJoinDTO.getPart())
                .userType(userJoinDTO.getUserType())
                .isTeamLeader(false)
                .teamMessage("")
                .teamId(userJoinDTO.getTeamId())
                .manitoTo(null)
                .manitoFrom(null)
                .build();

        if (
                newUser.getUserType().equals(Types.UserType.ROLE_MANAGER)
                && userRepository.findByTeamIdAndIsTeamLeaderTrue(newUser.getTeamId()).isEmpty()
        ){
            newUser.setTeamLeader(true);
        }

        userRepository.save(newUser);
    }

    public String loginUser(String name, String password){
        User selectedUser = userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(name));
        if(!encoder.matches(password, selectedUser.getPassword())){
            throw new InvalidPasswordException(name);
        }

        return JwtTokenProvider.createToken(
                selectedUser.getId(),selectedUser.getUserType().toString());
    }

    // ----------------------------------------------

    public void updateTeamMessage(Long managerId, String message){
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException(managerId.toString()));

        if (!manager.getUserType().equals(Types.UserType.ROLE_MANAGER)){
            throw new EntityNotManagerException(managerId);
        }

        manager.setTeamMessage(message);
        userRepository.save(manager);
    }

    public void updateTeamLeader(Long managerId){
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException(managerId.toString()));

        if (!manager.getUserType().equals(Types.UserType.ROLE_MANAGER)){
            throw new EntityNotManagerException(managerId);
        }

        User teamLeader = userRepository.findByTeamIdAndIsTeamLeaderTrue(manager.getTeamId())
                .orElseThrow(InvalidDBException::new);
        if (manager == teamLeader){
            return;
        }

        teamLeader.setTeamLeader(false);
        manager.setTeamLeader(true);

        userRepository.saveAll(Arrays.asList(manager, teamLeader));
    }
}
