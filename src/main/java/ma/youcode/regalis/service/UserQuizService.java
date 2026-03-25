package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.userquiz.UserQuizCreateDTO;
import ma.youcode.regalis.dto.userquiz.UserQuizResponseDTO;
import ma.youcode.regalis.dto.userquiz.UserQuizUpdateDTO;
import java.util.List;

public interface UserQuizService {
    UserQuizResponseDTO startQuiz(UserQuizCreateDTO dto);

    UserQuizResponseDTO updateProgress(Long id, UserQuizUpdateDTO dto);

    List<UserQuizResponseDTO> getUserQuiz(Long userId);

    UserQuizResponseDTO getProgressByQuiz(Long userId, Long quizId);
}
