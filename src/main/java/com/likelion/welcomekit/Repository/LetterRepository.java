package com.likelion.welcomekit.Repository;

import com.likelion.welcomekit.Domain.Entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
    List<Letter> findByTargetIdAndIsWelcomeFalse(Long targetId);

    // 1. l2와 targetId가 같고, isWelcome이 True인 값을 찾는다.
    // 2. 이를 sender로 그룹화하여 id가 가장 큰 l2를 선별한다.
    // 3. 이렇게 l을 전부 선별하여 모든 l을 추출한다.
    // -> 운영진들이 작성한 Letters 중 가장 최신 것만 반영하기 위함이다.
    // 사실 그냥 create 할때 기존꺼 지우게 하면 되는데 이렇게 해보고 싶었음
    @Query("SELECT l FROM Letter l WHERE l.id IN " +
            "(SELECT MAX(l2.id) FROM Letter l2 " +
            "WHERE l2.targetId = :targetId AND l2.isWelcome = TRUE " +
            "GROUP BY l2.senderId)")
    List<Letter> findWelcomeLettersByTargetId(@Param("targetId") Long targetId);
}
