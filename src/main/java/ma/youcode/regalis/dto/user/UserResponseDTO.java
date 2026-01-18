package ma.youcode.regalis.dto.user;

import ma.youcode.regalis.enums.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        Role role,
        Integer totalXp,
        Integer level,
        Integer currentStreak,
        LocalDate lastActiveDate,
        LocalDateTime createdAt) {
}
