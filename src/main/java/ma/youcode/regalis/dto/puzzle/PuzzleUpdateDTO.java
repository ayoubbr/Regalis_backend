package ma.youcode.regalis.dto.puzzle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PuzzleUpdateDTO(
        @NotBlank(message = "Title is required")
        String title,
        
        @NotBlank(message = "Description is required")
        String description,
        
        @NotNull(message = "Difficulty is required")
        Integer difficulty,
        
        @NotNull(message = "XP Reward is required")
        Integer xpReward,
        
        @NotNull(message = "Module ID is required")
        Long moduleId
) {}
