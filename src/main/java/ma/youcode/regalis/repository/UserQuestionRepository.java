package ma.youcode.regalis.repository;

import ma.youcode.regalis.entity.UserQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {
    List<UserQuestion> findByUserIdAndQuestionQuizId(Long userId, Long quizId);
    Optional<UserQuestion> findByUserIdAndQuestionId(Long userId, Long questionId);
}
