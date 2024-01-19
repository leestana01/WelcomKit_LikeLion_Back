package Repository;

import Domain.Entity.Manager;
import Domain.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<Manager> findManagerByName(String name);
    Optional<Manager> findManagerByTeamId(Long teamId);
}
