package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Domain.DTO.ProjectSettingDTO;
import com.likelion.welcomekit.Service.ProjectSettingService;
import com.likelion.welcomekit.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settings")
public class ProjectSettingController {
    private final ProjectSettingService projectSettingService;
    private final UserService userService;

    @GetMapping("/active")
    public ResponseEntity<?> getProjectSettingDB(){
        return ResponseEntity.ok(projectSettingService.getProjectSettingDB());
    }

    @GetMapping("/result")
    public ResponseEntity<?> getResultsOfGuessManito(){
        return ResponseEntity.ok(userService.getResultsOfGuessManito());
    }

    // 이 컨트롤러는 귀찮아서 ResponseEntity 안씀
    @PostMapping("/start")
    public String makeManito(){
        return projectSettingService.makeManito();
    }

    @PostMapping("/stop")
    public String stopManito(){
        return projectSettingService.stopManito();
    }

    // 어드민 전용
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/reset")
    public String resetManito(){
        return projectSettingService.resetManito();
    }
}
