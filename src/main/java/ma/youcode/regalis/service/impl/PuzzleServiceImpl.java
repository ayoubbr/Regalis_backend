package ma.youcode.regalis.service.impl;

import jakarta.persistence.criteria.Predicate;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<PuzzleResponseDTO> getAllPuzzles() {
        return puzzleRepository.findAll().stream()
                .map(puzzleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PuzzleResponseDTO> getPuzzlesByModuleId(Long moduleId) {
        return puzzleRepository.findByModuleId(moduleId).stream()
                .map(puzzleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PuzzleResponseDTO> getPagedPuzzles(String search, Long moduleId, Pageable pageable) {
        return puzzleRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {
                String pattern = "%" + search.toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
                ));
            }

            if (moduleId != null) {
                predicates.add(cb.equal(root.get("module").get("id"), moduleId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable).map(puzzleMapper::toDTO);
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

}
