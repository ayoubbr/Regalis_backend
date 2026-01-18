package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.userpuzzleattempt.UserPuzzleAttemptCreateDTO;
import ma.youcode.regalis.dto.userpuzzleattempt.UserPuzzleAttemptResponseDTO;
import java.util.List;

public interface UserPuzzleAttemptService {
    UserPuzzleAttemptResponseDTO recordAttempt(UserPuzzleAttemptCreateDTO dto);

    List<UserPuzzleAttemptResponseDTO> getUserAttempts(Long userId);
}
