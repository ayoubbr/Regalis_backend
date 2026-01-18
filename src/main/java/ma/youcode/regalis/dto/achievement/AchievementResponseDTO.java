package ma.youcode.regalis.dto.achievement;

import ma.youcode.regalis.enums.ConditionType;

public record AchievementResponseDTO(
        Long id,
        String name,
        String description,
        ConditionType conditionType,
        Integer conditionValue) {
}
