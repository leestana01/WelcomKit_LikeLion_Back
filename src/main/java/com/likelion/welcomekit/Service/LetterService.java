package com.likelion.welcomekit.Service;

import com.likelion.welcomekit.Domain.DTO.LetterRequestDTO;
import com.likelion.welcomekit.Domain.Entity.Letter;
import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Repository.LetterRepository;
import com.likelion.welcomekit.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final UserRepository userRepository;
    private final LetterRepository letterRepository;

    public Letter createLetter(Long senderId, LetterRequestDTO dto) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException(senderId.toString()));

        User target = userRepository.findById(dto.getTargetId())
                .orElseThrow(() -> new EntityNotFoundException(dto.getTargetId().toString()));

        Letter letter = Letter.builder()
                .sender(sender)
                .target(target)
                .message(dto.getMessage())
                .isWelcome(dto.isWelcome())
                .build();

        return letterRepository.save(letter);
    }
}
