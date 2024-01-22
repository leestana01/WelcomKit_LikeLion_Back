package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Domain.DTO.TeamMessageDTO;
import com.likelion.welcomekit.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/managers")
public class ManagerController {
    private final UserService userService;

    @PostMapping("/team-message")
    public ResponseEntity<String> updateTeamMessage(Authentication authentication, @RequestBody TeamMessageDTO teamMessageDTO){
        String message = teamMessageDTO.getMessage();
        Long userId = (Long) authentication.getPrincipal();
        userService.updateTeamMessage(userId, message);
        return ResponseEntity.ok("메시지 - "+message+"가 정상적으로 등록되었습니다.");
    }

    // 팀장 설정
    @PostMapping("/team-leader")
    public ResponseEntity<String> updateTeamLeader(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        userService.updateTeamLeader(userId);
        return ResponseEntity.ok("해당 팀의 리더가 정상적으로 변경되었습니다.");
    }

}
