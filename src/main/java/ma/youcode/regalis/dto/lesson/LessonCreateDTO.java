package ma.youcode.regalis.dto.lesson;

public record LessonCreateDTO(
        String title,
        String content,
        Integer difficulty,
        Integer xpReward,
        Long moduleId) {
}
