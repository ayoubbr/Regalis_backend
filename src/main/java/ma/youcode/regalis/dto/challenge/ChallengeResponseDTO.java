package ma.youcode.regalis.dto.challenge;

import ma.youcode.regalis.enums.ChallengeStatus;
import java.time.LocalDateTime;

public record ChallengeResponseDTO(
        Long id,
        ChallengeStatus status,
        Long winnerId,
        Long challengerId,
        Long opponentId,
        Long puzzleId,
        LocalDateTime createdAt) {
}
