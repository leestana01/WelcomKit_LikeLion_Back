package com.likelion.welcomekit.Service;

import com.likelion.welcomekit.Domain.DTO.UserJoinDTO;
import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Exception.InvalidPasswordException;
import com.likelion.welcomekit.Repository.UserRepository;
import com.likelion.welcomekit.Utils.JwtTokenProvider;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
                .teamId(0L)
                .manitoTo(null)
                .manitoFrom(null)
                .build();
        userRepository.save(newUser);
    }

    public String loginUser(String name, String password){
        User selectedUser = userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(name));
        if(!encoder.matches(password, selectedUser.getPassword())){
            throw new InvalidPasswordException(name);
        }

        return JwtTokenProvider.createToken(
                selectedUser.getName(),selectedUser.getUserType().toString());
    }

    public void updateTeamMessage(Long managerId, String message){
    }

    public void updateTeamLeader(Long managerId){
    }
}
