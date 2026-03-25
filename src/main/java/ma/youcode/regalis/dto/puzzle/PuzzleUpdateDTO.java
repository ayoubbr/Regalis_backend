package ma.youcode.regalis.dto.puzzle;

public record PuzzleUpdateDTO(
                String fenPosition,
                String title,
                String description,
                String solutionMoves,
                Integer difficulty,
                Integer xpReward,
                Integer maxAttempts,
                Long moduleId) {
}
