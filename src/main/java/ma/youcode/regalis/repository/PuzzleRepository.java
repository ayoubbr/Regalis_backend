package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, Long> {
    List<Puzzle> findByModuleId(Long moduleId);
}
