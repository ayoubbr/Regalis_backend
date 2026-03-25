package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.userpuzzle.UserPuzzleCreateDTO;
import ma.youcode.regalis.dto.userpuzzle.UserPuzzleResponseDTO;
import ma.youcode.regalis.entity.UserPuzzle;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserPuzzleMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "puzzle", ignore = true)
    @Mapping(target = "attemptDate", ignore = true)
    UserPuzzle toEntity(UserPuzzleCreateDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "puzzle.id", target = "puzzleId")
    UserPuzzleResponseDTO toDTO(UserPuzzle entity);
}
