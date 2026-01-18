package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.challenge.ChallengeCreateDTO;
import ma.youcode.regalis.dto.challenge.ChallengeResponseDTO;
import ma.youcode.regalis.dto.challenge.ChallengeUpdateDTO;
import ma.youcode.regalis.entity.Challenge;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {
    @Mapping(target = "challenger", ignore = true)
    @Mapping(target = "opponent", ignore = true)
    @Mapping(target = "puzzle", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "winnerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Challenge toEntity(ChallengeCreateDTO dto);

    @Mapping(source = "challenger.id", target = "challengerId")
    @Mapping(source = "opponent.id", target = "opponentId")
    @Mapping(source = "puzzle.id", target = "puzzleId")
    ChallengeResponseDTO toDTO(Challenge entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "challenger", ignore = true)
    @Mapping(target = "opponent", ignore = true)
    @Mapping(target = "puzzle", ignore = true)
    void updateEntityFromDTO(ChallengeUpdateDTO dto, @MappingTarget Challenge entity);
}
