package com.likelion.welcomekit.Service;

import com.likelion.welcomekit.Domain.DTO.LetterManitoRequestDTO;
import com.likelion.welcomekit.Domain.DTO.LetterWelcomeRequestDTO;
import com.likelion.welcomekit.Domain.DTO.LetterResponseDTO;
import com.likelion.welcomekit.Domain.Entity.Letter;
import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Domain.Types;
import com.likelion.welcomekit.Exception.EntityNotManagerException;
import com.likelion.welcomekit.Repository.LetterRepository;
import com.likelion.welcomekit.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final UserRepository userRepository;
    private final LetterRepository letterRepository;

    public void createManitoLetter(Long senderId, LetterManitoRequestDTO dto) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException(senderId.toString()));

        User target = sender.getManitoTo();
        if (target == null){
            throw new EntityNotFoundException("마니또 대상");
        }

        Letter letter = Letter.builder()
                .senderId(senderId)
                .targetId(target.getId())
                .message(dto.getMessage())
                .isWelcome(false)
                .build();

        letterRepository.save(letter);
    }

    public void createWelcomeLetter(Long senderId, LetterWelcomeRequestDTO dto){
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException(senderId.toString()));

        User target = userRepository.findById(dto.getTargetId())
                .orElseThrow(() -> new EntityNotFoundException(dto.getTargetId().toString()));

        if (sender.getUserType() == Types.UserType.ROLE_USER){
            throw new EntityNotManagerException(senderId);
        }

        Letter letter = Letter.builder()
                .senderId(senderId)
                .targetId(target.getId())
                .message(dto.getMessage())
                .isWelcome(true)
                .build();

        letterRepository.save(letter);
    }

    public List<LetterResponseDTO> findWelcomeLettersByTargetId(Long targetId) {
        // 내림차순 정렬하여 마지막 값 도출
        return letterRepository.findWelcomeLettersByTargetId(targetId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<LetterResponseDTO> findManitoLettersByTargetId(Long targetId) {
        return letterRepository.findByTargetIdAndIsWelcomeFalse(targetId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    private LetterResponseDTO toResponseDTO(Letter letter){
        User sender = userRepository.findById(letter.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException(letter.getSenderId().toString()));
        return new LetterResponseDTO(sender.getName(),letter.getMessage());
    }
}
