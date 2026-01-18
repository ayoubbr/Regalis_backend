package ma.youcode.regalis.dto.userpuzzleattempt;

import java.time.LocalDateTime;

public record UserPuzzleAttemptResponseDTO(
        Long id,
        Long userId,
        Long puzzleId,
        Integer attemptsCount,
        Boolean solved,
        Integer timeSpentSeconds,
        String submittedMoves,
        LocalDateTime attemptDate) {
}
