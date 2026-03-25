package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.userquiz.UserQuizCreateDTO;
import ma.youcode.regalis.dto.userquiz.UserQuizResponseDTO;
import ma.youcode.regalis.dto.userquiz.UserQuizUpdateDTO;
import ma.youcode.regalis.entity.Quiz;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.entity.UserQuiz;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.UserQuizMapper;
import ma.youcode.regalis.repository.QuizRepository;
import ma.youcode.regalis.repository.UserQuizRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.service.UserQuizService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserQuizServiceImpl implements UserQuizService {

    private final UserQuizRepository userQuizRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final UserQuizMapper userQuizMapper;

    @Override
    public UserQuizResponseDTO startQuiz(UserQuizCreateDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.userId()));
        Quiz quiz = quizRepository.findById(dto.quizId())
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id: " + dto.quizId()));

        UserQuiz progress = userQuizMapper.toEntity(dto);
        progress.setUser(user);
        progress.setQuiz(quiz);
        progress.setCompleted(false);

        UserQuiz savedProgress = userQuizRepository.save(progress);
        return userQuizMapper.toDTO(savedProgress);
    }

    @Override
    public UserQuizResponseDTO updateProgress(Long id, UserQuizUpdateDTO dto) {
        UserQuiz progress = userQuizRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserQuiz not found with id: " + id));

        userQuizMapper.updateEntityFromDTO(dto, progress);

        if (Boolean.TRUE.equals(dto.completed()) && progress.getCompletionDate() == null) {
            progress.setCompletionDate(LocalDateTime.now());
        }

        UserQuiz updatedProgress = userQuizRepository.save(progress);
        return userQuizMapper.toDTO(updatedProgress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserQuizResponseDTO> getUserQuiz(Long userId) {
        return userQuizRepository.findByUserId(userId).stream()
                .map(userQuizMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserQuizResponseDTO getProgressByQuiz(Long userId, Long quizId) {
        UserQuiz progress = userQuizRepository.findByUserIdAndQuizId(userId, quizId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Progress not found for user: " + userId + " and quiz: " + quizId));
        return userQuizMapper.toDTO(progress);
    }
}
