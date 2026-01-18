package ma.youcode.regalis.dto.achievement;

import ma.youcode.regalis.enums.ConditionType;

public record AchievementCreateDTO(
        String name,
        String description,
        ConditionType conditionType,
        Integer conditionValue) {
}
