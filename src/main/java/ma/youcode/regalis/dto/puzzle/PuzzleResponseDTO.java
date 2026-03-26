package ma.youcode.regalis.dto.puzzle;

import ma.youcode.regalis.dto.situation.SituationResponseDTO;
import java.util.List;

public record PuzzleResponseDTO(
                Long id,
                Integer difficulty,
                Integer xpReward,
                Long moduleId,
                List<SituationResponseDTO> situations) {
}
