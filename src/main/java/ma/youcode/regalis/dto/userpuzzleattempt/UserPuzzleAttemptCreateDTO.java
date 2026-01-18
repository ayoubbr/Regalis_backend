package ma.youcode.regalis.dto.userpuzzleattempt;

public record UserPuzzleAttemptCreateDTO(
        Long userId,
        Long puzzleId,
        Integer attemptsCount,
        Boolean solved,
        Integer timeSpentSeconds,
        String submittedMoves) {
}
