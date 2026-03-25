package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.userquiz.UserQuizCreateDTO;
import ma.youcode.regalis.dto.userquiz.UserQuizResponseDTO;
import ma.youcode.regalis.dto.userquiz.UserQuizUpdateDTO;
import ma.youcode.regalis.entity.UserQuiz;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserQuizMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "completionDate", ignore = true)
    @Mapping(target = "completed", ignore = true)
    UserQuiz toEntity(UserQuizCreateDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "quiz.id", target = "quizId")
    UserQuizResponseDTO toDTO(UserQuiz entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    void updateEntityFromDTO(UserQuizUpdateDTO dto, @MappingTarget UserQuiz entity);
}
