package com.likelion.welcomekit.Repository;

import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Domain.Types;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
//    Optional<User> findByUserType(Types.UserType userType);
    Optional<User> findByTeamIdAndIsTeamLeaderTrue(Long teamId);
}
