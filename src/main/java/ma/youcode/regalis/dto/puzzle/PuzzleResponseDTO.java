package ma.youcode.regalis.dto.puzzle;

import ma.youcode.regalis.dto.situation.SituationResponseDTO;
import java.util.List;

public record PuzzleResponseDTO(
                Long id,
                String title,
                String description,
                Integer difficulty,
                Integer xpReward,
                Long moduleId,
                String moduleName,
                List<SituationResponseDTO> situations) {
}
