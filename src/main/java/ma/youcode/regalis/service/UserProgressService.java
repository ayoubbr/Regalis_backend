package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.userprogress.UserProgressCreateDTO;
import ma.youcode.regalis.dto.userprogress.UserProgressResponseDTO;
import ma.youcode.regalis.dto.userprogress.UserProgressUpdateDTO;
import java.util.List;

public interface UserProgressService {
    UserProgressResponseDTO startLesson(UserProgressCreateDTO dto);

    UserProgressResponseDTO updateProgress(Long id, UserProgressUpdateDTO dto);

    List<UserProgressResponseDTO> getUserProgress(Long userId);

    UserProgressResponseDTO getProgressByLesson(Long userId, Long lessonId);
}
