package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByChallengerIdOrOpponentId(Long challengerId, Long opponentId);
}
