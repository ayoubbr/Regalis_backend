package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.userprogress.UserProgressCreateDTO;
import ma.youcode.regalis.dto.userprogress.UserProgressResponseDTO;
import ma.youcode.regalis.dto.userprogress.UserProgressUpdateDTO;
import ma.youcode.regalis.entity.Lesson;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.entity.UserProgress;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.UserProgressMapper;
import ma.youcode.regalis.repository.LessonRepository;
import ma.youcode.regalis.repository.UserProgressRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.service.UserProgressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProgressServiceImpl implements UserProgressService {

    private final UserProgressRepository userProgressRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final UserProgressMapper userProgressMapper;

    @Override
    public UserProgressResponseDTO startLesson(UserProgressCreateDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.userId()));
        Lesson lesson = lessonRepository.findById(dto.lessonId())
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + dto.lessonId()));

        UserProgress progress = userProgressMapper.toEntity(dto);
        progress.setUser(user);
        progress.setLesson(lesson);
        progress.setCompleted(false);

        UserProgress savedProgress = userProgressRepository.save(progress);
        return userProgressMapper.toDTO(savedProgress);
    }

    @Override
    public UserProgressResponseDTO updateProgress(Long id, UserProgressUpdateDTO dto) {
        UserProgress progress = userProgressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserProgress not found with id: " + id));

        userProgressMapper.updateEntityFromDTO(dto, progress);

        if (Boolean.TRUE.equals(dto.completed()) && progress.getCompletionDate() == null) {
            progress.setCompletionDate(LocalDateTime.now());
        }

        UserProgress updatedProgress = userProgressRepository.save(progress);
        return userProgressMapper.toDTO(updatedProgress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserProgressResponseDTO> getUserProgress(Long userId) {
        return userProgressRepository.findByUserId(userId).stream()
                .map(userProgressMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserProgressResponseDTO getProgressByLesson(Long userId, Long lessonId) {
        UserProgress progress = userProgressRepository.findByUserIdAndLessonId(userId, lessonId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Progress not found for user: " + userId + " and lesson: " + lessonId));
        return userProgressMapper.toDTO(progress);
    }
}
