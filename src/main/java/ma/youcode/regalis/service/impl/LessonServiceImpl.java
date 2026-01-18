package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.lesson.LessonCreateDTO;
import ma.youcode.regalis.dto.lesson.LessonResponseDTO;
import ma.youcode.regalis.dto.lesson.LessonUpdateDTO;
import ma.youcode.regalis.entity.Lesson;
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

        Lesson lesson = lessonMapper.toEntity(dto);
        lesson.setModule(module);
        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDTO(savedLesson);
    }

    @Override
    @Transactional(readOnly = true)
    public LessonResponseDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + id));
        return lessonMapper.toDTO(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonResponseDTO> getLessonsByModuleId(Long moduleId) {
        return lessonRepository.findByModuleId(moduleId).stream()
                .map(lessonMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessonResponseDTO updateLesson(Long id, LessonUpdateDTO dto) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + id));

        if (dto.moduleId() != null && !dto.moduleId().equals(lesson.getModule().getId())) {
            Module newModule = moduleRepository.findById(dto.moduleId())
                    .orElseThrow(() -> new EntityNotFoundException("Module not found with id: " + dto.moduleId()));
            lesson.setModule(newModule);
        }

        lessonMapper.updateEntityFromDTO(dto, lesson);
        Lesson updatedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDTO(updatedLesson);
    }

    @Override
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new EntityNotFoundException("Lesson not found with id: " + id);
        }
        lessonRepository.deleteById(id);
    }
}
