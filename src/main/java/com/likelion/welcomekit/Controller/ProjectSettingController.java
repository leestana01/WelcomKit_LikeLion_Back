package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Service.ProjectSettingService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings")
public class ProjectSettingController {
    private final ProjectSettingService projectSettingService;

    // 이 컨트롤러는 귀찮아서 ResponseEntity 안씀

    @GetMapping("/active")
    public boolean getProjectSettingDB(){
        return projectSettingService.getProjectSettingDB();
    }

    @PostMapping("/start")
    public String makeManito(){
        return projectSettingService.makeManito();
    }

    @PostMapping("/stop")
    public String stopManito(){
        return projectSettingService.stopManito();
    }

    // 어드민 전용
    @PostMapping("/reset")
    public String resetManito(){
        return projectSettingService.resetManito();
    }
}
