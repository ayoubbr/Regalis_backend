package ma.youcode.regalis.dto.userpuzzle;

public record UserPuzzleCreateDTO(
        Long userId,
        Long puzzleId,
        Integer attemptsCount,
        Boolean solved) {
}
