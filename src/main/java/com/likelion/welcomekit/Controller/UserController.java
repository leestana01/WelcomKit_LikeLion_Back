package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Domain.DTO.Login.UserPasswordRequestDTO;
import com.likelion.welcomekit.Domain.DTO.Team.TeamMessageDTO;
import com.likelion.welcomekit.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/mypage")
    public ResponseEntity<?> getMyPageInfo(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getMyPageInfo(userId));
    }

    @PreAuthorize("hasAnyRole('MANAGER','BOSS','ADMIN')")
    @GetMapping("/babylions")
    public ResponseEntity<?> getBabyLions(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getBabyLions(userId));
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

    @PostMapping("/team-message")
    public ResponseEntity<String> updateTeamMessage(Authentication authentication, @RequestBody TeamMessageDTO teamMessageDTO){
        String message = teamMessageDTO.getMessage();
        Long userId = (Long) authentication.getPrincipal();
        userService.updateTeamMessage(userId, message);
        return ResponseEntity.ok("메시지 - "+message+"가 정상적으로 등록되었습니다.");
    }

    @PostMapping("/password")
    public ResponseEntity<String> updatePassword(Authentication authentication, @RequestBody UserPasswordRequestDTO userPasswordRequestDTO){
        Long userId = (Long) authentication.getPrincipal();
        userService.updatePassword(userId, userPasswordRequestDTO.getPassword());
        return ResponseEntity.ok("비밀번호가 성공적으로 갱신되었습니다.");
    }
}
