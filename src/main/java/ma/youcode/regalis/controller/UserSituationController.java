package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.userpuzzle.UserSituationResponseDTO;
import ma.youcode.regalis.service.UserSituationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-situations")
@RequiredArgsConstructor
@Tag(name = "User Situation", description = "Open and complete a situation of a puzzle")
public class UserSituationController {
    private final UserSituationService userSituationService;

    @PostMapping("/{userId}/open/{situationId}")
    public ResponseEntity<UserSituationResponseDTO> openSituation(
            @PathVariable Long userId,
            @PathVariable Long situationId) {
        return ResponseEntity.ok(userSituationService.openSituation(userId, situationId));
    }

    @PostMapping("/{userId}/complete/{situationId}")
    public ResponseEntity<UserSituationResponseDTO> completeSituation(
            @PathVariable Long userId,
            @PathVariable Long situationId,
            @RequestParam String userMove) {
        return ResponseEntity.ok(userSituationService.completeSituation(userId, situationId, userMove));
    }
}
