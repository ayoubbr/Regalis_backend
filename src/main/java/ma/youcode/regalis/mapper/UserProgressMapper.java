package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.userprogress.UserProgressCreateDTO;
import ma.youcode.regalis.dto.userprogress.UserProgressResponseDTO;
import ma.youcode.regalis.dto.userprogress.UserProgressUpdateDTO;
import ma.youcode.regalis.entity.UserProgress;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserProgressMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "completionDate", ignore = true)
    @Mapping(target = "completed", ignore = true)
    UserProgress toEntity(UserProgressCreateDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "lesson.id", target = "lessonId")
    UserProgressResponseDTO toDTO(UserProgress entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    void updateEntityFromDTO(UserProgressUpdateDTO dto, @MappingTarget UserProgress entity);
}
