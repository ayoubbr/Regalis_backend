package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.UserSituation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface UserSituationRepository extends JpaRepository<UserSituation, Long> {
    Optional<UserSituation> findByUserIdAndSituationId(Long userId, Long situationId);
    List<UserSituation> findByUserIdAndSituationPuzzleId(Long userId, Long puzzleId);

    @Query("SELECT us.user.id, us.situation.puzzle.id, us.situation.puzzle.xpReward " +
           "FROM UserSituation us " +
           "WHERE us.isCorrect = true AND us.createdAt >= :start AND us.createdAt <= :end " +
           "GROUP BY us.user.id, us.situation.puzzle.id, us.situation.puzzle.xpReward")
    List<Object[]> sumPuzzleXpByUserInPeriod(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);
}
