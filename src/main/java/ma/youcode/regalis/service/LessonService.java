package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.lesson.LessonCreateDTO;
import ma.youcode.regalis.dto.lesson.LessonResponseDTO;
import ma.youcode.regalis.dto.lesson.LessonUpdateDTO;
import java.util.List;

public interface LessonService {
    LessonResponseDTO createLesson(LessonCreateDTO dto);

    LessonResponseDTO getLessonById(Long id);

    List<LessonResponseDTO> getLessonsByModuleId(Long moduleId);

    LessonResponseDTO updateLesson(Long id, LessonUpdateDTO dto);

    void deleteLesson(Long id);
}
