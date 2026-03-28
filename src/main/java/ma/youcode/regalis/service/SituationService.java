package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.situation.SituationCreateDTO;
import ma.youcode.regalis.dto.situation.SituationResponseDTO;
import ma.youcode.regalis.dto.situation.SituationUpdateDTO;

import java.util.List;

public interface SituationService {
    SituationResponseDTO create(SituationCreateDTO dto);
    SituationResponseDTO update(Long id, SituationUpdateDTO dto);
    void delete(Long id);
    List<SituationResponseDTO> getByPuzzleId(Long puzzleId);
}
