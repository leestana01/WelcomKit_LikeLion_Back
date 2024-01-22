package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Domain.DTO.LetterRequestDTO;
import com.likelion.welcomekit.Domain.DTO.LetterResponseDTO;
import com.likelion.welcomekit.Domain.Entity.Letter;
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

    @PostMapping("/create")
    public ResponseEntity<?> createLetter(Authentication authentication, @ModelAttribute LetterRequestDTO dto){
        Long userId = (Long) authentication.getPrincipal();
        letterService.createLetter(userId, dto);
        return ResponseEntity.ok("편지가 정상적으로 생성됨");
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
