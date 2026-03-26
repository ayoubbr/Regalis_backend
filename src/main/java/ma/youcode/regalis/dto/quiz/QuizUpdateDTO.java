package ma.youcode.regalis.dto.quiz;

public record QuizUpdateDTO(
        String title,
        String content,
        Integer difficulty,
        Long moduleId,
        String imageUrl) {
}
