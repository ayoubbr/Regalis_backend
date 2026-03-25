package ma.youcode.regalis.dto.situation;

public record SituationResponseDTO(
        Long id,
        String fenPosition,
        String solutionMoves,
        String description) {
}
