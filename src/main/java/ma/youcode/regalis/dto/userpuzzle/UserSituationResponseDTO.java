package ma.youcode.regalis.dto.userpuzzle;

public record UserSituationResponseDTO(
        Long id,
        Long userId,
        Long situationId,
        String userMove,
        Boolean isCorrect) {
}
