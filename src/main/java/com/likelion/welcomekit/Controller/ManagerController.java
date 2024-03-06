package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Domain.DTO.Info.UserCountByPartResponseDTO;
import com.likelion.welcomekit.Domain.DTO.Info.UserMyPageForManagerRequestDTO;
import com.likelion.welcomekit.Domain.DTO.Team.TeamMessageDTO;
import com.likelion.welcomekit.Domain.DTO.Info.UserInfoResponseDTO;
import com.likelion.welcomekit.Domain.DTO.UserUpdate.BabyLionUpdateDTO;
import com.likelion.welcomekit.Domain.DTO.UserUpdate.ManagerUpdateDTO;
import com.likelion.welcomekit.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {
    private final UserService userService;

    // TODO: 매니저 전용 메시지로 변경해야함.
    @PostMapping("/team-message")
    public ResponseEntity<String> updateTeamMessage(Authentication authentication, @RequestBody TeamMessageDTO teamMessageDTO){
        String message = teamMessageDTO.getMessage();
        Long userId = (Long) authentication.getPrincipal();
        userService.updateTeamMessage(userId, message);
        return ResponseEntity.ok("메시지 - "+message+"가 정상적으로 등록되었습니다.");
    }

    // 팀장 설정
    @PostMapping("/leader/{id}")
    public ResponseEntity<String> updateTeamLeader(@PathVariable("id") Long managerId){
        userService.updateTeamLeader(managerId);
        return ResponseEntity.ok("해당 팀의 리더가 정상적으로 변경되었습니다.");
    }

    @PostMapping("/mypage")
    public ResponseEntity<?> updateManagerInfo(Authentication authentication, @RequestBody UserMyPageForManagerRequestDTO dto){
        Long userId = (Long) authentication.getPrincipal();
        userService.updateManagerInfo(userId,dto);
        return ResponseEntity.ok("본인 매니저 정보가 성공적으로 변경되었습니다.");
    }

    @GetMapping("/teammates")
    public ResponseEntity<List<UserInfoResponseDTO>> getMyTeammatesForManager(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getMyTeammatesForManager(userId));
    }

    @GetMapping("/users")
    public ResponseEntity<UserCountByPartResponseDTO> getUserCountByPart(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserCountByPart());
    }

    // 유저 정보 일괄 수정 -------------------------------------------------------
    @GetMapping("/info/managers")
    public ResponseEntity<List<ManagerUpdateDTO>> getManagersForUpdate() {
        List<ManagerUpdateDTO> managers = userService.getManagersForUpdate();
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/info/babylions")
    public ResponseEntity<List<BabyLionUpdateDTO>> getBabyLionsForUpdate() {
        List<BabyLionUpdateDTO> users = userService.getUsersForUpdate();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/info/managers")
    public ResponseEntity<?> updateTeamAndLeaderInfo(@RequestBody List<ManagerUpdateDTO> managerUpdateDTOS) {
        userService.updateTeamAndLeaderInfo(managerUpdateDTOS);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/info/babylions")
    public ResponseEntity<?> updateBabyLionInfo(@RequestBody List<BabyLionUpdateDTO> babyLionUpdateDTOS) {
        userService.updateBabyLionInfo(babyLionUpdateDTOS);
        return ResponseEntity.ok().build();
    }

}
