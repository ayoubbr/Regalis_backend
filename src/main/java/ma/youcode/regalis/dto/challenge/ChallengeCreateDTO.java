package ma.youcode.regalis.dto.challenge;

public record ChallengeCreateDTO(
        Long challengerId,
        Long opponentId,
        Long puzzleId) {
}
