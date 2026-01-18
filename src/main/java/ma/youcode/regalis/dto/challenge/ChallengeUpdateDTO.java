package ma.youcode.regalis.dto.challenge;

import ma.youcode.regalis.enums.ChallengeStatus;

public record ChallengeUpdateDTO(
        ChallengeStatus status,
        Long winnerId) {
}
