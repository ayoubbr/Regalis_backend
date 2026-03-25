package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.quiz.QuizCreateDTO;
import ma.youcode.regalis.dto.quiz.QuizResponseDTO;
import ma.youcode.regalis.dto.quiz.QuizUpdateDTO;
import java.util.List;

public interface QuizService {
    QuizResponseDTO createQuiz(QuizCreateDTO dto);

    QuizResponseDTO getQuizById(Long id);

    List<QuizResponseDTO> getQuizzesByModuleId(Long moduleId);

    List<QuizResponseDTO> getAllQuizzes();

    QuizResponseDTO updateQuiz(Long id, QuizUpdateDTO dto);

    void deleteQuiz(Long id);
}
