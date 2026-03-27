package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserQuizRepository extends JpaRepository<UserQuiz, Long> {
    List<UserQuiz> findByUserId(Long userId);

    Optional<UserQuiz> findByUserIdAndQuizId(Long userId, Long lessonId);

    @Query("SELECT uq.user.id, COALESCE(SUM(uq.score), 0) FROM UserQuiz uq " +
           "WHERE uq.completionDate >= :start AND uq.completionDate <= :end " +
           "GROUP BY uq.user.id " +
           "ORDER BY COALESCE(SUM(uq.score), 0) DESC")
    List<Object[]> sumScoresByUserInPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
