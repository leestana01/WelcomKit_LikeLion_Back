package com.likelion.welcomekit.Utils;

import java.util.Random;

import com.likelion.welcomekit.Domain.DTO.UserJoinDTO;
import com.likelion.welcomekit.Domain.Types;
import com.likelion.welcomekit.Repository.UserRepository;
import com.likelion.welcomekit.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataGenerator {
    // DB 테스트를 위해 만든 서비스이므로, 사용 금지

    private static final Random random = new Random();
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final UserService userService;

    public void generateRandomUsers(int TOTAL_USERS, int TOTAL_TEAMS) {
        for (int i = 0; i < TOTAL_USERS; i++) {
            UserJoinDTO userJoinDTO = new UserJoinDTO();
            userJoinDTO.setName("User" + i);
            userJoinDTO.setPassword("Password" + i);
            userJoinDTO.setDepartment("Department" + (i % 5));
            userJoinDTO.setPart(randomPart());
            userJoinDTO.setUserType(randomUserType());
            userJoinDTO.setTeamId((long) (random.nextInt(TOTAL_TEAMS) + 1));

            userService.createUser(userJoinDTO);
        }
    }

    private Types.PartType randomPart() {
        Types.PartType[] parts = Types.PartType.values();
        return parts[random.nextInt(parts.length)];
    }

    private Types.UserType randomUserType() {
        Types.UserType[] userTypes = Types.UserType.values();
        return userTypes[random.nextInt(2)];
    }

}
