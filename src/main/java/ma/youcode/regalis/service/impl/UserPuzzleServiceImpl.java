package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.userpuzzle.UserPuzzleCreateDTO;
import ma.youcode.regalis.dto.userpuzzle.UserPuzzleResponseDTO;
import ma.youcode.regalis.entity.Puzzle;
import ma.youcode.regalis.entity.User;
import ma.youcode.regalis.entity.UserPuzzle;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.UserPuzzleMapper;
import ma.youcode.regalis.repository.PuzzleRepository;
import ma.youcode.regalis.repository.UserPuzzleRepository;
import ma.youcode.regalis.repository.UserRepository;
import ma.youcode.regalis.service.UserPuzzleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserPuzzleServiceImpl implements UserPuzzleService {

    private final UserPuzzleRepository userPuzzleRepository;
    private final UserRepository userRepository;
    private final PuzzleRepository puzzleRepository;
    private final UserPuzzleMapper userPuzzleMapper;

    @Override
    public UserPuzzleResponseDTO recordAttempt(UserPuzzleCreateDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.userId()));
        Puzzle puzzle = puzzleRepository.findById(dto.puzzleId())
                .orElseThrow(() -> new EntityNotFoundException("Puzzle not found with id: " + dto.puzzleId()));

        UserPuzzle attempt = userPuzzleMapper.toEntity(dto);
        attempt.setUser(user);
        attempt.setPuzzle(puzzle);
        attempt.setAttemptDate(LocalDateTime.now());

        UserPuzzle savedAttempt = userPuzzleRepository.save(attempt);
        return userPuzzleMapper.toDTO(savedAttempt);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPuzzleResponseDTO> getUserAttempts(Long userId) {
        return userPuzzleRepository.findByUserId(userId).stream()
                .map(userPuzzleMapper::toDTO)
                .collect(Collectors.toList());
    }
}
