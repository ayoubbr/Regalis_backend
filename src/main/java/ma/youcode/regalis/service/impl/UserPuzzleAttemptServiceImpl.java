package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.userpuzzleattempt.UserPuzzleAttemptCreateDTO;
import ma.youcode.regalis.dto.userpuzzleattempt.UserPuzzleAttemptResponseDTO;
import ma.youcode.regalis.entity.Puzzle;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.entity.UserPuzzleAttempt;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.UserPuzzleAttemptMapper;
import ma.youcode.regalis.repository.PuzzleRepository;
import ma.youcode.regalis.repository.UserPuzzleAttemptRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.service.UserPuzzleAttemptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserPuzzleAttemptServiceImpl implements UserPuzzleAttemptService {

    private final UserPuzzleAttemptRepository userPuzzleAttemptRepository;
    private final UserRepository userRepository;
    private final PuzzleRepository puzzleRepository;
    private final UserPuzzleAttemptMapper userPuzzleAttemptMapper;

    @Override
    public UserPuzzleAttemptResponseDTO recordAttempt(UserPuzzleAttemptCreateDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.userId()));
        Puzzle puzzle = puzzleRepository.findById(dto.puzzleId())
                .orElseThrow(() -> new EntityNotFoundException("Puzzle not found with id: " + dto.puzzleId()));

        UserPuzzleAttempt attempt = userPuzzleAttemptMapper.toEntity(dto);
        attempt.setUser(user);
        attempt.setPuzzle(puzzle);
        attempt.setAttemptDate(LocalDateTime.now());

        UserPuzzleAttempt savedAttempt = userPuzzleAttemptRepository.save(attempt);
        return userPuzzleAttemptMapper.toDTO(savedAttempt);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPuzzleAttemptResponseDTO> getUserAttempts(Long userId) {
        return userPuzzleAttemptRepository.findByUserId(userId).stream()
                .map(userPuzzleAttemptMapper::toDTO)
                .collect(Collectors.toList());
    }
}
