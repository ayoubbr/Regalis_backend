package ma.youcode.regalis.dto.admin;

import java.util.List;
import java.util.Map;

public record DashboardStatsDTO(
    long totalUsers,
    long activeUsers,
    long totalModules,
    long totalPuzzles,
    long totalQuizzes,
    long totalXp,
    List<DashboardActivityDTO> recentActivities,
    Map<String, Long> userGrowth
) {}
