package ma.youcode.regalis.dto.quiz;

import ma.youcode.regalis.dto.question.QuestionResponseDTO;
import java.util.List;

public record QuizResponseDTO(
        Long id,
        String title,
        String content,
        Integer difficulty,
        Integer xpReward,
        Long moduleId,
        String imageUrl,
        List<QuestionResponseDTO> questions) {
}
