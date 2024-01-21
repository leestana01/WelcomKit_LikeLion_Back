package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Domain.DTO.UserJoinDTO;
import com.likelion.welcomekit.Domain.DTO.UserLoginDTO;
import com.likelion.welcomekit.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> createUser(@ModelAttribute UserJoinDTO userJoinDTO) {
        userService.createUser(userJoinDTO);
        return ResponseEntity.ok().body(userJoinDTO.getName());
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@ModelAttribute UserLoginDTO userLoginDTO){
        String token = userService.loginUser(userLoginDTO.getName(), userLoginDTO.getPassword());
        return ResponseEntity.ok().body(token);
    }
}
