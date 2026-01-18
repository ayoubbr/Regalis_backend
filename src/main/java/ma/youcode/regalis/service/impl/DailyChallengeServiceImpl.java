package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.dailychallenge.DailyChallengeCreateDTO;
import ma.youcode.regalis.dto.dailychallenge.DailyChallengeResponseDTO;
import ma.youcode.regalis.dto.dailychallenge.DailyChallengeUpdateDTO;
import ma.youcode.regalis.entity.DailyChallenge;
import ma.youcode.regalis.entity.Puzzle;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.DailyChallengeMapper;
import ma.youcode.regalis.repository.DailyChallengeRepository;
import ma.youcode.regalis.repository.PuzzleRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.service.DailyChallengeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyChallengeServiceImpl implements DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;
    private final UserRepository userRepository;
    private final PuzzleRepository puzzleRepository;
    private final DailyChallengeMapper dailyChallengeMapper;

    @Override
    @Transactional(readOnly = true)
    public DailyChallengeResponseDTO getDailyChallengeForUser(Long userId, LocalDate date) {
        DailyChallenge challenge = dailyChallengeRepository.findByChallengeDateAndUserId(date, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Daily challenge not found for user: " + userId + " on date: " + date));
        return dailyChallengeMapper.toDTO(challenge);
    }

    @Override
    public DailyChallengeResponseDTO createDailyChallenge(DailyChallengeCreateDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.userId()));
        Puzzle puzzle = puzzleRepository.findById(dto.puzzleId())
                .orElseThrow(() -> new EntityNotFoundException("Puzzle not found with id: " + dto.puzzleId()));

        DailyChallenge challenge = dailyChallengeMapper.toEntity(dto);
        challenge.setUser(user);
        challenge.setPuzzle(puzzle);
        challenge.setCompleted(false);

        DailyChallenge savedChallenge = dailyChallengeRepository.save(challenge);
        return dailyChallengeMapper.toDTO(savedChallenge);
    }

    @Override
    public DailyChallengeResponseDTO markChallengeCompleted(Long id, DailyChallengeUpdateDTO dto) {
        DailyChallenge challenge = dailyChallengeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DailyChallenge not found with id: " + id));

        dailyChallengeMapper.updateEntityFromDTO(dto, challenge);
        DailyChallenge updatedChallenge = dailyChallengeRepository.save(challenge);
        return dailyChallengeMapper.toDTO(updatedChallenge);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailyChallengeResponseDTO> getUserChallengeHistory(Long userId) {
        return dailyChallengeRepository.findByUserId(userId).stream()
                .map(dailyChallengeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
