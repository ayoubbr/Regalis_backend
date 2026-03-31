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

    private final ma.youcode.regalis.repository.UserQuestionRepository userQuestionRepository;

    @Override
    public UserQuizResponseDTO updateProgress(Long id, UserQuizUpdateDTO dto) {
        UserQuiz progress = userQuizRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserQuiz not found with id: " + id));

        User user = progress.getUser();
        List<ma.youcode.regalis.entity.Question> quizQuestions = progress.getQuiz().getQuestions();
        int totalQuestions = quizQuestions.size();
        int correctCount = 0;
        int newScore = 0;

        if (dto.answers() != null) {
            for (UserQuizUpdateDTO.UserAnswerDTO answerDTO : dto.answers()) {
                ma.youcode.regalis.entity.Question question = quizQuestions.stream()
                        .filter(q -> q.getId().equals(answerDTO.questionId()))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + answerDTO.questionId()));

                boolean isCorrect = question.getCorrectOptionId().equals(answerDTO.selectedOptionId());
                
                // Update UserQuestion
                ma.youcode.regalis.entity.UserQuestion userQuestion = userQuestionRepository
                        .findByUserIdAndQuestionId(user.getId(), question.getId())
                        .orElse(new ma.youcode.regalis.entity.UserQuestion());
                
                userQuestion.setUser(user);
                userQuestion.setQuestion(question);
                userQuestion.setSelectedOptionId(answerDTO.selectedOptionId());
                userQuestion.setIsCorrect(isCorrect);
                userQuestionRepository.save(userQuestion);

                if (isCorrect) {
                  newScore += (question.getXpReward() != null ? question.getXpReward() : 0);
                  correctCount++;
                }
            }
        }

        // Completion logic: All answers must be correct
        boolean isAllCorrect = (correctCount == totalQuestions);
        progress.setCompleted(isAllCorrect);
        
        if (isAllCorrect && progress.getCompletionDate() == null) {
            progress.setCompletionDate(LocalDateTime.now());
        }

        // Update User XP logic: (New Score - Old Score)

        int oldScore = progress.getScore() != null ? progress.getScore() : 0;
        int diff = newScore - oldScore;
        if (diff != 0) {
            user.setTotalXp(user.getTotalXp() + diff);
            // Re-calculate level
            user.setLevel((user.getTotalXp() / 100) + 1);
            userRepository.save(user);
        }

        progress.setScore(newScore);
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
