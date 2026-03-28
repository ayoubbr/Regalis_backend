package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.situation.SituationCreateDTO;
import ma.youcode.regalis.dto.situation.SituationResponseDTO;
import ma.youcode.regalis.dto.situation.SituationUpdateDTO;
import ma.youcode.regalis.entity.Situation;
import org.mapstruct.*;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SituationMapper {
    @Mapping(target = "puzzle", ignore = true)
    @Mapping(target = "id", ignore = true)
    Situation toEntity(SituationCreateDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fenPosition", source = "fenPosition")
    @Mapping(target = "correctMove", source = "correctMove")
    @Mapping(target = "description", source = "description")
    SituationResponseDTO toDTO(Situation entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "puzzle", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(SituationUpdateDTO dto, @MappingTarget Situation entity);
}
