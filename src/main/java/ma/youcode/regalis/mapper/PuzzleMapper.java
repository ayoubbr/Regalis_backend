package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.puzzle.PuzzleCreateDTO;
import ma.youcode.regalis.dto.puzzle.PuzzleResponseDTO;
import ma.youcode.regalis.dto.puzzle.PuzzleUpdateDTO;
import ma.youcode.regalis.entity.Puzzle;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PuzzleMapper {
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "puzzleAttempts", ignore = true)
    @Mapping(target = "dailyChallenges", ignore = true)
    Puzzle toEntity(PuzzleCreateDTO dto);

    @Mapping(source = "module.id", target = "moduleId")
    PuzzleResponseDTO toDTO(Puzzle entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "module", ignore = true)
    void updateEntityFromDTO(PuzzleUpdateDTO dto, @MappingTarget Puzzle entity);
}
