package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.challenge.ChallengeCreateDTO;
import ma.youcode.regalis.dto.challenge.ChallengeResponseDTO;
import ma.youcode.regalis.dto.challenge.ChallengeUpdateDTO;
import java.util.List;

public interface ChallengeService {
    ChallengeResponseDTO createChallenge(ChallengeCreateDTO dto);

    ChallengeResponseDTO getChallengeById(Long id);

    ChallengeResponseDTO updateChallengeStatus(Long id, ChallengeUpdateDTO dto);

    List<ChallengeResponseDTO> getChallengesForUser(Long userId);
}
