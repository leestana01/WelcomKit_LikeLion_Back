package Service;

import Domain.Entity.Letter;
import Domain.Entity.User;
import Repository.LetterRepository;
import Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final UserRepository userRepository;
    private final LetterRepository letterRepository;

    public Letter createLetter(Long senderId, Long targetId, String message) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found with id: " + senderId));

        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new EntityNotFoundException("Target not found with id: " + targetId));

        Letter letter = Letter.builder()
                .sender(sender)
                .target(target)
                .message(message)
                .isWelcome(false)
                .build();

        return letterRepository.save(letter);
    }
}
