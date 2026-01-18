package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.dailychallenge.DailyChallengeCreateDTO;
import ma.youcode.regalis.dto.dailychallenge.DailyChallengeResponseDTO;
import ma.youcode.regalis.dto.dailychallenge.DailyChallengeUpdateDTO;
import java.time.LocalDate;
import java.util.List;

public interface DailyChallengeService {
    DailyChallengeResponseDTO getDailyChallengeForUser(Long userId, LocalDate date);

    DailyChallengeResponseDTO createDailyChallenge(DailyChallengeCreateDTO dto);

    DailyChallengeResponseDTO markChallengeCompleted(Long id, DailyChallengeUpdateDTO dto);

    List<DailyChallengeResponseDTO> getUserChallengeHistory(Long userId);
}
