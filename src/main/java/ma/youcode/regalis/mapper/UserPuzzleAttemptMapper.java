package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.userpuzzleattempt.UserPuzzleAttemptCreateDTO;
import ma.youcode.regalis.dto.userpuzzleattempt.UserPuzzleAttemptResponseDTO;
import ma.youcode.regalis.entity.UserPuzzleAttempt;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserPuzzleAttemptMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "puzzle", ignore = true)
    @Mapping(target = "attemptDate", ignore = true)
    UserPuzzleAttempt toEntity(UserPuzzleAttemptCreateDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "puzzle.id", target = "puzzleId")
    UserPuzzleAttemptResponseDTO toDTO(UserPuzzleAttempt entity);
}
