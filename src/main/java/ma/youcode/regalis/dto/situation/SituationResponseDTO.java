package ma.youcode.regalis.dto.situation;

public record SituationResponseDTO(
        Long id,
        String fenPosition,
        String correctMove,
        String description,
        Long puzzleId) {
}
