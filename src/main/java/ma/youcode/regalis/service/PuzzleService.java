package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.puzzle.PuzzleCreateDTO;
import ma.youcode.regalis.dto.puzzle.PuzzleResponseDTO;
import ma.youcode.regalis.dto.puzzle.PuzzleUpdateDTO;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PuzzleService {
    PuzzleResponseDTO createPuzzle(PuzzleCreateDTO dto);

    PuzzleResponseDTO getPuzzleById(Long id);

    List<PuzzleResponseDTO> getAllPuzzles();

    List<PuzzleResponseDTO> getPuzzlesByModuleId(Long moduleId);

    Page<PuzzleResponseDTO> getPagedPuzzles(String search, Long moduleId, Pageable pageable);

    PuzzleResponseDTO updatePuzzle(Long id, PuzzleUpdateDTO dto);

    void deletePuzzle(Long id);

}
