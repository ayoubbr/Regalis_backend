package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.challenge.ChallengeCreateDTO;
import ma.youcode.regalis.dto.challenge.ChallengeResponseDTO;
import ma.youcode.regalis.dto.challenge.ChallengeUpdateDTO;
import ma.youcode.regalis.entity.Challenge;
import ma.youcode.regalis.entity.Puzzle;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.enums.ChallengeStatus;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.ChallengeMapper;
import ma.youcode.regalis.repository.ChallengeRepository;
import ma.youcode.regalis.repository.PuzzleRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.service.ChallengeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final PuzzleRepository puzzleRepository;
    private final ChallengeMapper challengeMapper;

    @Override
    public ChallengeResponseDTO createChallenge(ChallengeCreateDTO dto) {
        User challenger = userRepository.findById(dto.challengerId())
                .orElseThrow(() -> new EntityNotFoundException("Challenger not found with id: " + dto.challengerId()));
        User opponent = userRepository.findById(dto.opponentId())
                .orElseThrow(() -> new EntityNotFoundException("Opponent not found with id: " + dto.opponentId()));
        Puzzle puzzle = puzzleRepository.findById(dto.puzzleId())
                .orElseThrow(() -> new EntityNotFoundException("Puzzle not found with id: " + dto.puzzleId()));

        Challenge challenge = challengeMapper.toEntity(dto);
        challenge.setChallenger(challenger);
        challenge.setOpponent(opponent);
        challenge.setPuzzle(puzzle);
        challenge.setStatus(ChallengeStatus.PENDING);
        challenge.setCreatedAt(LocalDateTime.now());

        Challenge savedChallenge = challengeRepository.save(challenge);
        return challengeMapper.toDTO(savedChallenge);
    }

    @Override
    @Transactional(readOnly = true)
    public ChallengeResponseDTO getChallengeById(Long id) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Challenge not found with id: " + id));
        return challengeMapper.toDTO(challenge);
    }

    @Override
    public ChallengeResponseDTO updateChallengeStatus(Long id, ChallengeUpdateDTO dto) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Challenge not found with id: " + id));

        challengeMapper.updateEntityFromDTO(dto, challenge);
        Challenge updatedChallenge = challengeRepository.save(challenge);
        return challengeMapper.toDTO(updatedChallenge);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChallengeResponseDTO> getChallengesForUser(Long userId) {
        return challengeRepository.findByChallengerIdOrOpponentId(userId, userId).stream()
                .map(challengeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
