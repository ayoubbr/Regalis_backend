package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.dailychallenge.DailyChallengeCreateDTO;
import ma.youcode.regalis.dto.dailychallenge.DailyChallengeResponseDTO;
import ma.youcode.regalis.dto.dailychallenge.DailyChallengeUpdateDTO;
import ma.youcode.regalis.entity.DailyChallenge;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DailyChallengeMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "puzzle", ignore = true)
    @Mapping(target = "completed", ignore = true)
    DailyChallenge toEntity(DailyChallengeCreateDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "puzzle.id", target = "puzzleId")
    DailyChallengeResponseDTO toDTO(DailyChallenge entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "puzzle", ignore = true)
    void updateEntityFromDTO(DailyChallengeUpdateDTO dto, @MappingTarget DailyChallenge entity);
}
