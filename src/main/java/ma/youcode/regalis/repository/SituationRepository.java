package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SituationRepository extends JpaRepository<Situation, Long> {
    
    @Query("SELECT s FROM Situation s WHERE s.puzzle.id = :puzzleId")
    List<Situation> findByPuzzleId(@Param("puzzleId") Long puzzleId);
}
