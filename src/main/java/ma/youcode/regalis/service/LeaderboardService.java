package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.leaderboardentry.LeaderboardEntryResponseDTO;
import ma.youcode.regalis.enums.Period;
import java.util.List;

public interface LeaderboardService {
    List<LeaderboardEntryResponseDTO> getLeaderboard(Period period);

    void updateLeaderboard(Period period);
}
