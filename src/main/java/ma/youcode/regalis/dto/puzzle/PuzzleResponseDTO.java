package ma.youcode.regalis.dto.puzzle;

public record PuzzleResponseDTO(
        Long id,
        String fenPosition,
        String solutionMoves,
        Integer difficulty,
        Integer xpReward,
        Integer maxAttempts,
        Long moduleId) {
}
