package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String hello(){
        return "hello";
    }

//    // 팀원에게 남기는 메시지
//    @PostMapping("/team/message/{id}")
//    public ResponseEntity<String> updateTeamMessage(@PathVariable Long id, String message){
//        userService.updateTeamMessage(id, message);
//        return ResponseEntity.ok("메시지 - "+message+"가 정상적으로 등록되었습니다.");
//    }
//
//    // 팀장 설정
//    @PostMapping("/team/leader/{id}")
//    public ResponseEntity<String> updateTeamLeader(@PathVariable Long managerId){
//        userService.updateTeamLeader(managerId);
//        return ResponseEntity.ok("해당 팀의 리더가 정상적으로 변경되었습니다.");
//    }
}
