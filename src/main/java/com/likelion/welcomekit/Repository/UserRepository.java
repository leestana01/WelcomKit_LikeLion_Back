package com.likelion.welcomekit.Repository;

import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Domain.Types;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
//    Optional<User> findByUserType(Types.UserType userType);
    Optional<User> findByTeamIdAndIsTeamLeaderTrue(Long teamId);
    List<User> findByTeamId(Long teamId);
    List<User> findAllByUserType(Types.UserType userType);
    List<User> findByUserTypeNot(Types.UserType userType);


    long countByUserType(Types.UserType userType);

    @Query("select count(u) from User u where u.part = ?1")
    long countByPart(Types.PartType partType);
}
