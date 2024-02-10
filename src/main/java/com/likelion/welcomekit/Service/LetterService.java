package com.likelion.welcomekit.Service;

import com.likelion.welcomekit.Domain.DTO.Letters.LetterManitoRequestDTO;
import com.likelion.welcomekit.Domain.DTO.Letters.LetterManitoResponseDTO;
import com.likelion.welcomekit.Domain.DTO.Letters.LetterWelcomeResponseDTO;
import com.likelion.welcomekit.Domain.DTO.Letters.LetterWelcomeRequestDTO;
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

    public List<LetterWelcomeResponseDTO> findWelcomeLettersByTargetId(Long targetId) {
        // 내림차순 정렬하여 마지막 값 도출
        return letterRepository.findWelcomeLettersByTargetId(targetId)
                .stream().map(this::toWelcomeResponseDTO).collect(Collectors.toList());
    }

    public List<LetterManitoResponseDTO> findManitoLettersByTargetId(Long targetId) {
        return letterRepository.findByTargetIdAndIsWelcomeFalse(targetId)
                .stream()
                .map(letter -> new LetterManitoResponseDTO(letter.getMessage()))
                .collect(Collectors.toList());
    }

    public List<LetterManitoResponseDTO> findManitoLettersByMe(Long myId) {
        User sender = userRepository.findById(myId)
                .orElseThrow(() -> new EntityNotFoundException(myId.toString()));

        return letterRepository.findByTargetIdAndIsWelcomeFalse(sender.getManitoTo().getId())
                .stream()
                .map(letter -> new LetterManitoResponseDTO(letter.getMessage()))
                .collect(Collectors.toList());
    }

    private LetterWelcomeResponseDTO toWelcomeResponseDTO(Letter letter){
        User sender = userRepository.findById(letter.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException(letter.getSenderId().toString()));
        return new LetterWelcomeResponseDTO(sender.getName(),sender.getPart(),letter.getMessage(), sender.getProfileUrl(), sender.getProfileMiniUrl());
    }
}
