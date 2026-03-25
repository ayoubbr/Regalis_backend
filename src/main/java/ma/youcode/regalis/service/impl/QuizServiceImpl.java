package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.lesson.LessonCreateDTO;
import ma.youcode.regalis.dto.lesson.LessonResponseDTO;
import ma.youcode.regalis.dto.lesson.LessonUpdateDTO;
import ma.youcode.regalis.entity.Quiz;
import ma.youcode.regalis.entity.Module;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.LessonMapper;
import ma.youcode.regalis.repository.LessonRepository;
import ma.youcode.regalis.repository.ModuleRepository;
import ma.youcode.regalis.service.LessonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;
    private final LessonMapper lessonMapper;

    @Override
    public LessonResponseDTO createLesson(LessonCreateDTO dto) {
        Module module = moduleRepository.findById(dto.moduleId())
                .orElseThrow(() -> new EntityNotFoundException("Module not found with id: " + dto.moduleId()));

        Quiz quiz = lessonMapper.toEntity(dto);
        quiz.setModule(module);
        Quiz savedQuiz = lessonRepository.save(quiz);
        return lessonMapper.toDTO(savedQuiz);
    }

    @Override
    @Transactional(readOnly = true)
    public LessonResponseDTO getLessonById(Long id) {
        Quiz quiz = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + id));
        return lessonMapper.toDTO(quiz);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponseDTO> getLessonsByModuleId(Long moduleId) {
        return lessonRepository.findByModuleId(moduleId).stream()
                .map(lessonMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponseDTO> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(lessonMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessonResponseDTO updateLesson(Long id, LessonUpdateDTO dto) {
        Quiz quiz = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + id));

        if (dto.moduleId() != null && !dto.moduleId().equals(quiz.getModule().getId())) {
            Module newModule = moduleRepository.findById(dto.moduleId())
                    .orElseThrow(() -> new EntityNotFoundException("Module not found with id: " + dto.moduleId()));
            quiz.setModule(newModule);
        }

        lessonMapper.updateEntityFromDTO(dto, quiz);
        Quiz updatedQuiz = lessonRepository.save(quiz);
        return lessonMapper.toDTO(updatedQuiz);
    }

    @Override
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new EntityNotFoundException("Lesson not found with id: " + id);
        }
        lessonRepository.deleteById(id);
    }
}
