package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.situation.SituationCreateDTO;
import ma.youcode.regalis.dto.situation.SituationResponseDTO;
import ma.youcode.regalis.dto.situation.SituationUpdateDTO;
import ma.youcode.regalis.entity.Puzzle;
import ma.youcode.regalis.entity.Situation;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.SituationMapper;
import ma.youcode.regalis.repository.PuzzleRepository;
import ma.youcode.regalis.repository.SituationRepository;
import ma.youcode.regalis.service.SituationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SituationServiceImpl implements SituationService {

    private final SituationRepository situationRepository;
    private final PuzzleRepository puzzleRepository;
    private final SituationMapper situationMapper;

    @Override
    public SituationResponseDTO create(SituationCreateDTO dto) {
        Puzzle puzzle = puzzleRepository.findById(dto.puzzleId())
                .orElseThrow(() -> new EntityNotFoundException("Puzzle not found"));

        Situation situation = situationMapper.toEntity(dto);
        situation.setPuzzle(puzzle);
        
        Situation saved = situationRepository.save(situation);
        return situationMapper.toDTO(saved);
    }

    @Override
    public SituationResponseDTO update(Long id, SituationUpdateDTO dto) {
        Situation situation = situationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Situation not found"));

        situationMapper.updateEntityFromDTO(dto, situation);
        
        if (dto.puzzleId() != null && !dto.puzzleId().equals(situation.getPuzzle().getId())) {
             Puzzle puzzle = puzzleRepository.findById(dto.puzzleId())
                .orElseThrow(() -> new EntityNotFoundException("Puzzle not found"));
             situation.setPuzzle(puzzle);
        }

        Situation updated = situationRepository.save(situation);
        return situationMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!situationRepository.existsById(id)) {
            throw new EntityNotFoundException("Situation not found");
        }
        situationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SituationResponseDTO> getByPuzzleId(Long puzzleId) {
        return situationRepository.findByPuzzleId(puzzleId).stream()
                .map(situationMapper::toDTO)
                .collect(Collectors.toList());
    }
}

