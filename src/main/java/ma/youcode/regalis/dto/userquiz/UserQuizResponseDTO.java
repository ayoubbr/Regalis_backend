package ma.youcode.regalis.dto.userquiz;

import java.time.LocalDateTime;

public record UserQuizResponseDTO(
        Long id,
        Long userId,
        Long quizId,
        Boolean completed,
        LocalDateTime completionDate) {
}
