package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.puzzle.PuzzleCreateDTO;
import ma.youcode.regalis.dto.puzzle.PuzzleResponseDTO;
import ma.youcode.regalis.dto.puzzle.PuzzleUpdateDTO;
import java.util.List;

public interface PuzzleService {
    PuzzleResponseDTO createPuzzle(PuzzleCreateDTO dto);

    PuzzleResponseDTO getPuzzleById(Long id);

    List<PuzzleResponseDTO> getPuzzlesByModuleId(Long moduleId);

    PuzzleResponseDTO updatePuzzle(Long id, PuzzleUpdateDTO dto);

    void deletePuzzle(Long id);

    boolean verifySolution(Long puzzleId, String submittedMoves);
}
