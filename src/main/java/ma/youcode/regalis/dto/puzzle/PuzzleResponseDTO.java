package ma.youcode.regalis.dto.puzzle;

import ma.youcode.regalis.dto.situation.SituationResponseDTO;
import java.util.List;

public record PuzzleResponseDTO(
                Long id,
                String fenPosition,
                String title,
                String description,
                String solutionMoves,
                Integer difficulty,
                Integer xpReward,
                Integer maxAttempts,
                Long moduleId,
                List<SituationResponseDTO> situations) {
}
