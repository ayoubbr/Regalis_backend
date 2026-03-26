package ma.youcode.regalis.dto.puzzle;

public record PuzzleCreateDTO(
                Integer difficulty,
                Integer xpReward,
                Long moduleId) {
}
