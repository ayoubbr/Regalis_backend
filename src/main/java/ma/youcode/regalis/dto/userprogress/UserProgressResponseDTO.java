package ma.youcode.regalis.dto.userprogress;

import java.time.LocalDateTime;

public record UserProgressResponseDTO(
        Long id,
        Long userId,
        Long lessonId,
        Boolean completed,
        LocalDateTime completionDate) {
}
