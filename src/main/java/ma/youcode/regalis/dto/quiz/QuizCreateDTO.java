package ma.youcode.regalis.dto.quiz;

public record QuizCreateDTO(
        String title,
        String content,
        Integer difficulty,
        Long moduleId,
        String imageUrl) {
}
