package ma.youcode.regalis.dto.puzzle;

public record PuzzleCreateDTO(
        String fenPosition,
        String solutionMoves,
        Integer difficulty,
        Integer xpReward,
        Integer maxAttempts,
        Long moduleId) {
}
