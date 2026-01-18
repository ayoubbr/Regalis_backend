package ma.youcode.regalis.dto.dailychallenge;

import java.time.LocalDate;

public record DailyChallengeResponseDTO(
        Long id,
        LocalDate challengeDate,
        Long puzzleId,
        Long userId,
        Boolean completed) {
}
