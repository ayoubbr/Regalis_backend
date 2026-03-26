package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.UserSituation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserSituationRepository extends JpaRepository<UserSituation, Long> {
    Optional<UserSituation> findByUserIdAndSituationId(Long userId, Long situationId);
    List<UserSituation> findByUserIdAndSituationPuzzleId(Long userId, Long puzzleId);
}
