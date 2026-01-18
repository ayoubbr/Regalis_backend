package ma.youcode.regalis.dto.userachievement;

import java.time.LocalDateTime;

public record UserAchievementResponseDTO(
        Long id,
        Long userId,
        Long achievementId,
        LocalDateTime unlockedDate) {
}
