package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.userpuzzle.UserSituationResponseDTO;

public interface UserSituationService {
    UserSituationResponseDTO openSituation(Long userId, Long situationId);
    UserSituationResponseDTO completeSituation(Long userId, Long situationId, String userMove);
}
