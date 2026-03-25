package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.question.QuestionResponseDTO;
import ma.youcode.regalis.dto.quiz.QuizCreateDTO;
import ma.youcode.regalis.dto.quiz.QuizResponseDTO;
import ma.youcode.regalis.dto.quiz.QuizUpdateDTO;
import ma.youcode.regalis.entity.Quiz;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface QuizMapper {
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "userQuizzes", ignore = true)
    Quiz toEntity(QuizCreateDTO dto);

    @Mapping(source = "module.id", target = "moduleId")
    QuizResponseDTO toDTO(Quiz entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "module", ignore = true)
    void updateEntityFromDTO(QuizUpdateDTO dto, @MappingTarget Quiz entity);
}
