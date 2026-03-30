package ma.youcode.regalis.service.impl;

import ma.youcode.regalis.dto.quiz.QuizCreateDTO;
import ma.youcode.regalis.dto.quiz.QuizResponseDTO;
import ma.youcode.regalis.entity.Module;
import ma.youcode.regalis.entity.Quiz;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.QuizMapper;
import ma.youcode.regalis.repository.ModuleRepository;
import ma.youcode.regalis.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private QuizMapper quizMapper;

    @InjectMocks
    private QuizServiceImpl quizService;

    private Quiz quiz;
    private Module module;
    private QuizCreateDTO createDTO;
    private QuizResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        module = new Module();
        module.setId(1L);

        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Test Quiz");
        quiz.setModule(module);

        createDTO = new QuizCreateDTO("Test Quiz", "Content", 0, 1L, "test-image.jpg");
        responseDTO = new QuizResponseDTO(1L, "Test Quiz", "Content", 0, 1L, "test-image.jpg", Collections.emptyList());
    }

    @Test
    void createQuiz_WhenModuleExists_ShouldReturnQuizResponseDTO() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));
        when(quizMapper.toEntity(createDTO)).thenReturn(quiz);
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);
        when(quizMapper.toDTO(quiz)).thenReturn(responseDTO);

        QuizResponseDTO result = quizService.createQuiz(createDTO);

        assertNotNull(result);
        assertEquals(responseDTO.title(), result.title());
        verify(quizRepository).save(any(Quiz.class));
    }

    @Test
    void createQuiz_WhenModuleDoesNotExist_ShouldThrowException() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> quizService.createQuiz(createDTO));
        verify(quizRepository, never()).save(any(Quiz.class));
    }

    @Test
    void getQuizById_WhenQuizExists_ShouldReturnQuizResponseDTO() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(quizMapper.toDTO(quiz)).thenReturn(responseDTO);

        QuizResponseDTO result = quizService.getQuizById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    void getQuizById_WhenQuizDoesNotExist_ShouldThrowException() {
        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> quizService.getQuizById(1L));
    }

    @Test
    void deleteQuiz_WhenQuizExists_ShouldCallDelete() {
        when(quizRepository.existsById(1L)).thenReturn(true);

        quizService.deleteQuiz(1L);

        verify(quizRepository).deleteById(1L);
    }

    @Test
    void deleteQuiz_WhenQuizDoesNotExist_ShouldThrowException() {
        when(quizRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> quizService.deleteQuiz(1L));
        verify(quizRepository, never()).deleteById(anyLong());
    }
}
