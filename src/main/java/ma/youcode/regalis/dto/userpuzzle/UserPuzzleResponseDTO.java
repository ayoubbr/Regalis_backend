package ma.youcode.regalis.dto.userpuzzle;

import java.time.LocalDateTime;

public record UserPuzzleResponseDTO(
        Long id,
        Long userId,
        Long puzzleId,
        Integer attemptsCount,
        Boolean solved,
        Integer timeSpentSeconds,
        String submittedMoves,
        LocalDateTime attemptDate) {
}
