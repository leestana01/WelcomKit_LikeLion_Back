package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Domain.DTO.LetterManitoRequestDTO;
import com.likelion.welcomekit.Domain.DTO.LetterWelcomeRequestDTO;
import com.likelion.welcomekit.Domain.DTO.LetterResponseDTO;
import com.likelion.welcomekit.Service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/letters")
public class LetterController {
    private final LetterService letterService;

    @PostMapping("/create/welcome")
    public ResponseEntity<?> createWelcomeLetter(Authentication authentication, @ModelAttribute LetterWelcomeRequestDTO dto){
        Long userId = (Long) authentication.getPrincipal();
        letterService.createWelcomeLetter(userId, dto);
        return ResponseEntity.ok("웰컴키트 편지가 정상적으로 생성됨");
    }

    @PostMapping("/create/manito")
    public ResponseEntity<?> createManitoLetter(Authentication authentication, @ModelAttribute LetterManitoRequestDTO dto){
        Long userId = (Long) authentication.getPrincipal();
        letterService.createManitoLetter(userId, dto);
        return ResponseEntity.ok("마니또 편지가 정상적으로 생성됨");
    }

    @GetMapping("/welcome")
    public ResponseEntity<List<LetterResponseDTO>> findWelcomeLettersByTargetId(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(letterService.findWelcomeLettersByTargetId(userId));
    }

    @GetMapping("/manito")
    public ResponseEntity<List<LetterResponseDTO>> findManitoLettersByTargetId(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(letterService.findManitoLettersByTargetId(userId));
    }
}
