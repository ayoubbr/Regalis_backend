package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.UserPuzzleAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserPuzzleAttemptRepository extends JpaRepository<UserPuzzleAttempt, Long> {
    List<UserPuzzleAttempt> findByUserId(Long userId);
}
