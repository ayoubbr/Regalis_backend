package ma.youcode.regalis.dto.userpuzzle;

import java.time.LocalDateTime;

public record UserPuzzleResponseDTO(
        Long id,
        Long userId,
        Long puzzleId,
        Integer attemptsCount,
        Boolean solved,
        LocalDateTime createdAt) {
}
