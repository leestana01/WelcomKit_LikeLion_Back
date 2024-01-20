package com.likelion.welcomekit.Repository;

import com.likelion.welcomekit.Domain.Entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterRepository extends JpaRepository<Letter, Long> {
}
