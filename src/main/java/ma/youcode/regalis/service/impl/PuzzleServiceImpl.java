package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.puzzle.PuzzleCreateDTO;
import ma.youcode.regalis.dto.puzzle.PuzzleResponseDTO;
import ma.youcode.regalis.dto.puzzle.PuzzleUpdateDTO;
import ma.youcode.regalis.entity.Module;
import ma.youcode.regalis.entity.Puzzle;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.PuzzleMapper;
import ma.youcode.regalis.repository.ModuleRepository;
import ma.youcode.regalis.repository.PuzzleRepository;
import ma.youcode.regalis.service.PuzzleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PuzzleServiceImpl implements PuzzleService {

    private final PuzzleRepository puzzleRepository;
    private final ModuleRepository moduleRepository;
    private final PuzzleMapper puzzleMapper;

    @Override
    public PuzzleResponseDTO createPuzzle(PuzzleCreateDTO dto) {
        Module module = moduleRepository.findById(dto.moduleId())
                .orElseThrow(() -> new EntityNotFoundException("Module not found with id: " + dto.moduleId()));

        Puzzle puzzle = puzzleMapper.toEntity(dto);
        puzzle.setModule(module);
        Puzzle savedPuzzle = puzzleRepository.save(puzzle);
        return puzzleMapper.toDTO(savedPuzzle);
    }

    @Override
    @Transactional(readOnly = true)
    public PuzzleResponseDTO getPuzzleById(Long id) {
        Puzzle puzzle = puzzleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Puzzle not found with id: " + id));
        return puzzleMapper.toDTO(puzzle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PuzzleResponseDTO> getPuzzlesByModuleId(Long moduleId) {
        return puzzleRepository.findByModuleId(moduleId).stream()
                .map(puzzleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PuzzleResponseDTO updatePuzzle(Long id, PuzzleUpdateDTO dto) {
        Puzzle puzzle = puzzleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Puzzle not found with id: " + id));

        if (dto.moduleId() != null && !dto.moduleId().equals(puzzle.getModule().getId())) {
            Module newModule = moduleRepository.findById(dto.moduleId())
                    .orElseThrow(() -> new EntityNotFoundException("Module not found with id: " + dto.moduleId()));
            puzzle.setModule(newModule);
        }

        puzzleMapper.updateEntityFromDTO(dto, puzzle);
        Puzzle updatedPuzzle = puzzleRepository.save(puzzle);
        return puzzleMapper.toDTO(updatedPuzzle);
    }

    @Override
    public void deletePuzzle(Long id) {
        if (!puzzleRepository.existsById(id)) {
            throw new EntityNotFoundException("Puzzle not found with id: " + id);
        }
        puzzleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifySolution(Long puzzleId, String submittedMoves) {
        Puzzle puzzle = puzzleRepository.findById(puzzleId)
                .orElseThrow(() -> new EntityNotFoundException("Puzzle not found with id: " + puzzleId));

        String correctSolution = puzzle.getSolutionMoves();

        // Normalize moves for comparison (remove extra spaces, convert to lowercase)
        String[] correctMoveArray = normalizeMoves(correctSolution);
        String[] submittedMoveArray = normalizeMoves(submittedMoves);

        // Check if the submitted moves match the solution sequence
        if (correctMoveArray.length != submittedMoveArray.length) {
            return false;
        }

        for (int i = 0; i < correctMoveArray.length; i++) {
            if (!correctMoveArray[i].equals(submittedMoveArray[i])) {
                return false;
            }
        }

        return true;
    }

    private String[] normalizeMoves(String moves) {
        if (moves == null || moves.isBlank()) {
            return new String[0];
        }
        return Arrays.stream(moves.trim().split("[,\\s]+"))
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }
}
