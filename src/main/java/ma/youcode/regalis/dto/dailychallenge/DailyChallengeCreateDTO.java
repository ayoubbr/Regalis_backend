package ma.youcode.regalis.dto.dailychallenge;

import java.time.LocalDate;

public record DailyChallengeCreateDTO(
        LocalDate challengeDate,
        Long puzzleId,
        Long userId) {
}
