package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.userpuzzle.UserSituationResponseDTO;
import ma.youcode.regalis.entity.*;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.repository.*;
import ma.youcode.regalis.service.UserSituationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSituationServiceImpl implements UserSituationService {

    private final UserSituationRepository userSituationRepository;
    private final UserRepository userRepository;
    private final SituationRepository situationRepository;
    private final UserPuzzleRepository userPuzzleRepository;

    @Override
    public UserSituationResponseDTO openSituation(Long userId, Long situationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Situation situation = situationRepository.findById(situationId)
                .orElseThrow(() -> new EntityNotFoundException("Situation not found"));

        UserSituation userSituation = userSituationRepository.findByUserIdAndSituationId(userId, situationId)
                .orElseGet(() -> {
                    UserSituation newUs = UserSituation.builder()
                            .user(user)
                            .situation(situation)
                            .completed(false)
                            .build();
                    
                    // Increment attempt count on UserPuzzle whenever a new situation is opened
                    UserPuzzle up = userPuzzleRepository.findByUserIdAndPuzzleId(userId, situation.getPuzzle().getId())
                            .orElseGet(() -> UserPuzzle.builder()
                                    .user(user)
                                    .puzzle(situation.getPuzzle())
                                    .attemptsCount(0)
                                    .solved(false)
                                    .build());
                    up.setAttemptsCount(up.getAttemptsCount() + 1);
                    userPuzzleRepository.save(up);
                    
                    return userSituationRepository.save(newUs);
                });

        return new UserSituationResponseDTO(
                userSituation.getId(),
                userSituation.getUser().getId(),
                userSituation.getSituation().getId(),
                userSituation.getUserMove(),
                userSituation.getIsCorrect(),
                userSituation.getCompleted()
        );
    }

    @Override
    public UserSituationResponseDTO completeSituation(Long userId, Long situationId, String userMove) {
        UserSituation userSituation = userSituationRepository.findByUserIdAndSituationId(userId, situationId)
                .orElseThrow(() -> new EntityNotFoundException("UserSituation record not found. Must be opened first."));

        Situation situation = userSituation.getSituation();
        boolean isCorrect = situation.getCorrectMove().equalsIgnoreCase(userMove);

        userSituation.setUserMove(userMove);
        userSituation.setIsCorrect(isCorrect);
        userSituation.setCompleted(isCorrect);
        
        UserSituation saved = userSituationRepository.save(userSituation);

        if (isCorrect) {
            checkAndAwardPuzzleCompletion(userId, situation.getPuzzle());
        }

        return new UserSituationResponseDTO(
                saved.getId(),
                saved.getUser().getId(),
                saved.getSituation().getId(),
                saved.getUserMove(),
                saved.getIsCorrect(),
                saved.getCompleted()
        );
    }

    private void checkAndAwardPuzzleCompletion(Long userId, Puzzle puzzle) {
        List<Situation> allSituations = puzzle.getSituations();
        List<UserSituation> userSituations = userSituationRepository.findByUserIdAndSituationPuzzleId(userId, puzzle.getId());

        long solvedCount = userSituations.stream()
                .filter(us -> us.getIsCorrect() != null && us.getIsCorrect())
                .map(us -> us.getSituation().getId())
                .distinct()
                .count();

        if (solvedCount == allSituations.size() && !allSituations.isEmpty()) {
            UserPuzzle up = userPuzzleRepository.findByUserIdAndPuzzleId(userId, puzzle.getId())
                    .orElseThrow(() -> new EntityNotFoundException("UserPuzzle record not found"));
            
            if (!up.getSolved()) {
                up.setSolved(true);
                userPuzzleRepository.save(up);

                User user = up.getUser();
                user.setTotalXp((user.getTotalXp() != null ? user.getTotalXp() : 0) + puzzle.getXpReward());
                // Level logic: 0-99 XP = Level 1, 100-199 XP = Level 2, etc. (xp / 100 + 1)
                user.setLevel((user.getTotalXp() / 100) + 1);
                userRepository.save(user);
            }
        }
    }
}
