package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.achievement.AchievementCreateDTO;
import ma.youcode.regalis.dto.achievement.AchievementResponseDTO;
import ma.youcode.regalis.dto.achievement.AchievementUpdateDTO;
import ma.youcode.regalis.dto.userachievement.UserAchievementResponseDTO;
import java.util.List;

public interface AchievementService {
    AchievementResponseDTO createAchievement(AchievementCreateDTO dto);

    List<AchievementResponseDTO> getAllAchievements();

    AchievementResponseDTO updateAchievement(Long id, AchievementUpdateDTO dto);

    void deleteAchievement(Long id);

    // User Achievements logic
    List<UserAchievementResponseDTO> getUserAchievements(Long userId);

    void checkAndUnlockAchievements(Long userId);
}
