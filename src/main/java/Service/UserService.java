package Service;

import Domain.DTO.UserDTO;
import Domain.Entity.Manager;
import Domain.Entity.User;
import Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private void createUser(UserDTO userDTO){
        User newUser = UserFactory.createUser(userDTO);
        userRepository.save(newUser);
    }

    private void updateTeamMassage(Long managerId, String massage){
        Manager manager = (Manager) userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException(managerId.toString()));
        manager.setTeamMassage(massage);
        userRepository.save(manager);
    }

    private void updateTeamLeader(Long managerId, Long teamId){
        Manager manager = (Manager) userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException(managerId.toString()));

        userRepository.findManagerByTeamId(teamId)
                .ifPresent( foundManager -> {
                    foundManager.setTeamLeader(false);
                });

        manager.setTeamLeader(true);
        userRepository.save(manager);
    }
}
