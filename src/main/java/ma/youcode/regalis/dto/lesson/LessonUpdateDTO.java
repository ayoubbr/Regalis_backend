package ma.youcode.regalis.dto.lesson;

public record LessonUpdateDTO(
        String title,
        String content,
        Integer difficulty,
        Integer xpReward,
        Long moduleId) {
}
