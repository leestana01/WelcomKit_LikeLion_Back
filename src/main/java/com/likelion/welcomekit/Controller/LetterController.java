package com.likelion.welcomekit.Controller;

import com.likelion.welcomekit.Domain.DTO.LetterRequestDTO;
import com.likelion.welcomekit.Service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/letters")
public class LetterController {
    private final LetterService letterService;

//    @PostMapping("/create")
//    public ResponseEntity<?> createLetter(@RequestBody LetterRequestDTO dto){
//        letterService.createLetter(dto);
//        return ResponseEntity.ok("편지가 정상적으로 생성됨");
//    }
}
