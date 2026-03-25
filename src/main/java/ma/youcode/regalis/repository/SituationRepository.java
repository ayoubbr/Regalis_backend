package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SituationRepository extends JpaRepository<Situation, Long> {
    List<Situation> findByPuzzleId(Long puzzleId);
}
