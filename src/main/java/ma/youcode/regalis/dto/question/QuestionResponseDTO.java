package ma.youcode.regalis.dto.question;

public record QuestionResponseDTO(
        Long id,
        String text,
        String options,
        String correctOptionId,
        String hint,
        Integer xpReward) {
}
