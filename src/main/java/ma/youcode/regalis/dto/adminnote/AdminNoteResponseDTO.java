package ma.youcode.regalis.dto.adminnote;

import java.time.LocalDateTime;

public record AdminNoteResponseDTO(
        Long id,
        String message,
        Long userId,
        LocalDateTime createdAt) {
}
