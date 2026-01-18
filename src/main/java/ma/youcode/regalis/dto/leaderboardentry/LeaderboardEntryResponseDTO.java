package ma.youcode.regalis.dto.leaderboardentry;

import ma.youcode.regalis.enums.Period;

public record LeaderboardEntryResponseDTO(
        Long id,
        Period period,
        Integer xp,
        Integer rank,
        Long userId) {
}
