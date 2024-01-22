package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/teammates")
    public ResponseEntity<?> getMyTeammates(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getMyTeammates(userId));
    }

    @GetMapping("/myinfo")
    public ResponseEntity<?> getMyInfo(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getMyInfo(userId));
    }

    @PostMapping("/profile-img")
    public ResponseEntity<?> uploadProfileImage(Authentication authentication, @RequestParam("image") MultipartFile image){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.uploadProfileImage(userId, image));
    }

    @PostMapping("/profile-img/mini")
    public ResponseEntity<?> uploadProfileMiniImage(Authentication authentication, @RequestParam("image") MultipartFile image){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.uploadProfileMiniImage(userId, image));
    }


}
