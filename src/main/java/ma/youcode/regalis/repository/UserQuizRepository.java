package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserQuizRepository extends JpaRepository<UserQuiz, Long> {
    List<UserQuiz> findByUserId(Long userId);

    Optional<UserQuiz> findByUserIdAndQuizId(Long userId, Long lessonId);
}
