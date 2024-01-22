package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Utils.UserDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserDataGenerator userDataGenerator;

    @PostMapping("/sample-data")
    public String generateRandomUsers() {
        userDataGenerator.generateRandomUsers(100,10);
        return "성공적으로 랜덤 유저가 생성되었습니다.";
    }
}
