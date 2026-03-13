package ma.youcode.regalis.dto.admin;

import java.util.List;

public record DashboardStatsDTO(
    long totalUsers,
    long activeUsers,
    long totalModules,
    long totalPuzzles,
    long totalXp,
    List<DashboardActivityDTO> recentActivities
) {}
