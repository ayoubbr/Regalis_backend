package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.question.QuestionCreateDTO;
import ma.youcode.regalis.dto.question.QuestionResponseDTO;
import ma.youcode.regalis.dto.question.QuestionUpdateDTO;
import ma.youcode.regalis.entity.Question;
import ma.youcode.regalis.entity.Quiz;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.QuestionMapper;
import ma.youcode.regalis.repository.QuestionRepository;
import ma.youcode.regalis.repository.QuizRepository;
import ma.youcode.regalis.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuestionResponseDTO createQuestion(QuestionCreateDTO dto) {
        Quiz quiz = quizRepository.findById(dto.quizId())
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + dto.quizId()));

        Question question = questionMapper.toEntity(dto);
        question.setQuiz(quiz);
        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toDTO(savedQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionResponseDTO getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + id));
        return questionMapper.toDTO(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> getQuestionsByQuizId(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new EntityNotFoundException("Quiz not found with id: " + quizId);
        }
        return questionRepository.findByQuizId(quizId).stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionResponseDTO updateQuestion(Long id, QuestionUpdateDTO dto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + id));

        if (dto.quizId() != null && !dto.quizId().equals(question.getQuiz().getId())) {
            Quiz newQuiz = quizRepository.findById(dto.quizId())
                    .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + dto.quizId()));
            question.setQuiz(newQuiz);
        }

        questionMapper.updateEntityFromDTO(dto, question);
        Question updatedQuestion = questionRepository.save(question);
        return questionMapper.toDTO(updatedQuestion);
    }

    @Override
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new EntityNotFoundException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }
}
