package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.DailyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyChallengeRepository extends JpaRepository<DailyChallenge, Long> {
    Optional<DailyChallenge> findByChallengeDateAndUserId(LocalDate date, Long userId);

    List<DailyChallenge> findByUserId(Long userId);
}
