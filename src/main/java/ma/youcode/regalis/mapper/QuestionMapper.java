package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.question.QuestionCreateDTO;
import ma.youcode.regalis.dto.question.QuestionResponseDTO;
import ma.youcode.regalis.dto.question.QuestionUpdateDTO;
import ma.youcode.regalis.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    Question toEntity(QuestionCreateDTO dto);

    QuestionResponseDTO toDTO(Question question);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    void updateEntityFromDTO(QuestionUpdateDTO dto, @MappingTarget Question question);
}
