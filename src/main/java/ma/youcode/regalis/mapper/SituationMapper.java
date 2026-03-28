package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.situation.SituationCreateDTO;
import ma.youcode.regalis.dto.situation.SituationResponseDTO;
import ma.youcode.regalis.dto.situation.SituationUpdateDTO;
import ma.youcode.regalis.entity.Situation;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SituationMapper {
    @Mapping(target = "puzzle", ignore = true)
    @Mapping(target = "id", ignore = true)
    Situation toEntity(SituationCreateDTO dto);

    @Mapping(target = "puzzleId", source = "puzzle.id")
    SituationResponseDTO toDTO(Situation entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "puzzle", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(SituationUpdateDTO dto, @MappingTarget Situation entity);
}
