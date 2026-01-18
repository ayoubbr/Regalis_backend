package ma.youcode.regalis.dto.achievement;

import ma.youcode.regalis.enums.ConditionType;

public record AchievementUpdateDTO(
        String name,
        String description,
        ConditionType conditionType,
        Integer conditionValue) {
}
