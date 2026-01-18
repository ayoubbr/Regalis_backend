package ma.youcode.regalis.dto.lesson;

public record LessonResponseDTO(
        Long id,
        String title,
        String content,
        Integer difficulty,
        Integer xpReward,
        Long moduleId) {
}
