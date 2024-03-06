package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Domain.DTO.Letters.LetterManitoRequestDTO;
import com.likelion.welcomekit.Domain.DTO.Letters.LetterManitoResponseDTO;
import com.likelion.welcomekit.Domain.DTO.Letters.LetterWelcomeResponseDTO;
import com.likelion.welcomekit.Domain.DTO.Letters.LetterWelcomeRequestDTO;
import com.likelion.welcomekit.Service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letters")
public class LetterController {
    private final LetterService letterService;

    @PostMapping("/create/welcome")
    public ResponseEntity<?> createWelcomeLetter(Authentication authentication, @RequestBody LetterWelcomeRequestDTO dto){
        Long userId = (Long) authentication.getPrincipal();
        letterService.createWelcomeLetter(userId, dto);
        return ResponseEntity.ok("웰컴키트 편지가 정상적으로 생성됨");
    }

    @PostMapping("/create/manito")
    public ResponseEntity<?> createManitoLetter(Authentication authentication, @RequestBody LetterManitoRequestDTO dto){
        Long userId = (Long) authentication.getPrincipal();
        letterService.createManitoLetter(userId, dto);
        return ResponseEntity.ok("마니또 편지가 정상적으로 생성됨");
    }

    @GetMapping("/welcome")
    public ResponseEntity<List<LetterWelcomeResponseDTO>> findWelcomeLettersByTargetId(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(letterService.findWelcomeLettersByTargetId(userId));
    }

    @GetMapping("/welcome/{targetId}")
    public ResponseEntity<LetterWelcomeResponseDTO> findWelcomeLettersForEditByTargetId(Authentication authentication, @PathVariable("targetId") Long targetId){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(letterService.findWelcomeLetterByTargetId(userId, targetId));
    }

    @GetMapping("/manito")
    public ResponseEntity<List<LetterManitoResponseDTO>> findManitoLettersByTargetId(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(letterService.findManitoLettersByTargetId(userId));
    }

    @GetMapping("/manito/mine")
    public ResponseEntity<List<LetterManitoResponseDTO>> findManitoLettersByMe(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(letterService.findManitoLettersByMe(userId));
    }
}
