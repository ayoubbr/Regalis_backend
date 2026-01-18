package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.achievement.AchievementCreateDTO;
import ma.youcode.regalis.dto.achievement.AchievementResponseDTO;
import ma.youcode.regalis.dto.achievement.AchievementUpdateDTO;
import ma.youcode.regalis.dto.userachievement.UserAchievementResponseDTO;
import ma.youcode.regalis.entity.Achievement;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.entity.UserAchievement;
import ma.youcode.regalis.enums.ConditionType;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.AchievementMapper;
import ma.youcode.regalis.mapper.UserAchievementMapper;
import ma.youcode.regalis.repository.AchievementRepository;
import ma.youcode.regalis.repository.UserAchievementRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.service.AchievementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;
    private final AchievementMapper achievementMapper;
    private final UserAchievementMapper userAchievementMapper;

    @Override
    public AchievementResponseDTO createAchievement(AchievementCreateDTO dto) {
        Achievement achievement = achievementMapper.toEntity(dto);
        Achievement savedAchievement = achievementRepository.save(achievement);
        return achievementMapper.toDTO(savedAchievement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AchievementResponseDTO> getAllAchievements() {
        return achievementRepository.findAll().stream()
                .map(achievementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AchievementResponseDTO updateAchievement(Long id, AchievementUpdateDTO dto) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Achievement not found with id: " + id));
        achievementMapper.updateEntityFromDTO(dto, achievement);
        Achievement updatedAchievement = achievementRepository.save(achievement);
        return achievementMapper.toDTO(updatedAchievement);
    }

    @Override
    public void deleteAchievement(Long id) {
        if (!achievementRepository.existsById(id)) {
            throw new EntityNotFoundException("Achievement not found with id: " + id);
        }
        achievementRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAchievementResponseDTO> getUserAchievements(Long userId) {
        return userAchievementRepository.findByUserId(userId).stream()
                .map(userAchievementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void checkAndUnlockAchievements(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Get already unlocked achievement IDs
        Set<Long> unlockedIds = userAchievementRepository.findByUserId(userId).stream()
                .map(ua -> ua.getAchievement().getId())
                .collect(Collectors.toSet());

        // Check all achievements
        List<Achievement> allAchievements = achievementRepository.findAll();

        for (Achievement achievement : allAchievements) {
            if (unlockedIds.contains(achievement.getId())) {
                continue; // Already unlocked
            }

            boolean unlocked = checkCondition(user, achievement);

            if (unlocked) {
                UserAchievement userAchievement = new UserAchievement();
                userAchievement.setUser(user);
                userAchievement.setAchievement(achievement);
                userAchievement.setUnlockedDate(LocalDateTime.now());
                userAchievementRepository.save(userAchievement);
            }
        }
    }

    private boolean checkCondition(User user, Achievement achievement) {
        ConditionType type = achievement.getConditionType();
        Integer value = achievement.getConditionValue();

        if (type == null || value == null) {
            return false;
        }

        return switch (type) {
            case STREAK -> user.getCurrentStreak() != null && user.getCurrentStreak() >= value;
            case XP -> user.getTotalXp() != null && user.getTotalXp() >= value;
            case PUZZLES_SOLVED -> false; // Would need to count from UserPuzzleAttempt - simplified for now
        };
    }
}
