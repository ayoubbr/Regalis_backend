package ma.youcode.regalis.dto.userquiz;

import java.time.LocalDateTime;

public record UserQuizResponseDTO(
        Long id,
        Long quizId,
        String quizTitle,
        Boolean completed,
        Integer score,
        LocalDateTime completionDate) {
}
