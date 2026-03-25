package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.UserPuzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserPuzzleRepository extends JpaRepository<UserPuzzle, Long> {
    List<UserPuzzle> findByUserId(Long userId);
}
    