package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.lesson.LessonCreateDTO;
import ma.youcode.regalis.dto.lesson.LessonResponseDTO;
import ma.youcode.regalis.dto.lesson.LessonUpdateDTO;
import ma.youcode.regalis.entity.Lesson;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "userProgresses", ignore = true)
    Lesson toEntity(LessonCreateDTO dto);

    @Mapping(source = "module.id", target = "moduleId")
    LessonResponseDTO toDTO(Lesson entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "module", ignore = true)
    void updateEntityFromDTO(LessonUpdateDTO dto, @MappingTarget Lesson entity);
}
