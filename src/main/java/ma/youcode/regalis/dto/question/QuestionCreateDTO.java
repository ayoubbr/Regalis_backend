package ma.youcode.regalis.dto.question;

public record QuestionCreateDTO(
        Long quizId,
        String text,
        String options,
        String correctOptionId,
        String hint,
        Integer xpReward
) {
}
