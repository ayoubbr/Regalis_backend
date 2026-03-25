package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.userpuzzle.UserPuzzleCreateDTO;
import ma.youcode.regalis.dto.userpuzzle.UserPuzzleResponseDTO;
import java.util.List;

public interface UserPuzzleService {
    UserPuzzleResponseDTO recordAttempt(UserPuzzleCreateDTO dto);

    List<UserPuzzleResponseDTO> getUserAttempts(Long userId);
}
