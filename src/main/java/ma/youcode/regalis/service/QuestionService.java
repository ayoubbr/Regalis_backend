package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.question.QuestionCreateDTO;
import ma.youcode.regalis.dto.question.QuestionResponseDTO;
import ma.youcode.regalis.dto.question.QuestionUpdateDTO;

import java.util.List;

public interface QuestionService {
    QuestionResponseDTO createQuestion(QuestionCreateDTO dto);
    QuestionResponseDTO getQuestionById(Long id);
    List<QuestionResponseDTO> getQuestionsByQuizId(Long quizId);
    QuestionResponseDTO updateQuestion(Long id, QuestionUpdateDTO dto);
    void deleteQuestion(Long id);
}
