package ma.youcode.regalis.dto.situation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SituationCreateDTO(
        @NotBlank(message = "FEN position is required")
        String fenPosition,
        
        @NotBlank(message = "Correct move is required")
        String correctMove,
        
        String description,
        
        @NotNull(message = "Puzzle ID is required")
        Long puzzleId
) {}
