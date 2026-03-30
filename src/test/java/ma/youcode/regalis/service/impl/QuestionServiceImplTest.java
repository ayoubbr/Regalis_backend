package ma.youcode.regalis.service.impl;

import ma.youcode.regalis.dto.question.QuestionCreateDTO;
import ma.youcode.regalis.dto.question.QuestionResponseDTO;
import ma.youcode.regalis.entity.Question;
import ma.youcode.regalis.entity.Quiz;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.QuestionMapper;
import ma.youcode.regalis.repository.QuestionRepository;
import ma.youcode.regalis.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private Question question;
    private Quiz quiz;
    private QuestionCreateDTO createDTO;
    private QuestionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        quiz = new Quiz();
        quiz.setId(1L);

        question = new Question();
        question.setId(1L);
        question.setText("Test Question");
        question.setQuiz(quiz);

        createDTO = new QuestionCreateDTO(1L, "Test Question", "Option1;Option2", "Option1", "Hint", 50);
        
        responseDTO = new QuestionResponseDTO(1L, "Test Question", "Option1;Option2", "Option1", "Hint", 50);
    }

    @Test
    void createQuestion_WhenQuizExists_ShouldReturnQuestionResponseDTO() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(questionMapper.toEntity(createDTO)).thenReturn(question);
        when(questionRepository.save(any(Question.class))).thenReturn(question);
        when(questionMapper.toDTO(question)).thenReturn(responseDTO);

        QuestionResponseDTO result = questionService.createQuestion(createDTO);

        assertNotNull(result);
        assertEquals(responseDTO.text(), result.text());
        verify(questionRepository).save(any(Question.class));
    }

    @Test
    void createQuestion_WhenQuizDoesNotExist_ShouldThrowException() {
        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> questionService.createQuestion(createDTO));
        verify(questionRepository, never()).save(any(Question.class));
    }

    @Test
    void getQuestionById_WhenQuestionExists_ShouldReturnQuestionResponseDTO() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(questionMapper.toDTO(question)).thenReturn(responseDTO);

        QuestionResponseDTO result = questionService.getQuestionById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    void getQuestionById_WhenQuestionDoesNotExist_ShouldThrowException() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> questionService.getQuestionById(1L));
    }

    @Test
    void deleteQuestion_WhenQuestionExists_ShouldCallDelete() {
        when(questionRepository.existsById(1L)).thenReturn(true);

        questionService.deleteQuestion(1L);

        verify(questionRepository).deleteById(1L);
    }

    @Test
    void deleteQuestion_WhenQuestionDoesNotExist_ShouldThrowException() {
        when(questionRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> questionService.deleteQuestion(1L));
        verify(questionRepository, never()).deleteById(anyLong());
    }
}
