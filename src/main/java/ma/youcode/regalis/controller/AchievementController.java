package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.achievement.AchievementCreateDTO;
import ma.youcode.regalis.dto.achievement.AchievementResponseDTO;
import ma.youcode.regalis.dto.achievement.AchievementUpdateDTO;
import ma.youcode.regalis.dto.userachievement.UserAchievementResponseDTO;
import ma.youcode.regalis.service.AchievementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@RequiredArgsConstructor
@Tag(name = "Achievements", description = "Achievement management")
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping
    @Operation(summary = "Create a new achievement")
    public ResponseEntity<AchievementResponseDTO> createAchievement(@RequestBody AchievementCreateDTO dto) {
        return new ResponseEntity<>(achievementService.createAchievement(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all achievements")
    public ResponseEntity<List<AchievementResponseDTO>> getAllAchievements() {
        return ResponseEntity.ok(achievementService.getAllAchievements());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update achievement")
    public ResponseEntity<AchievementResponseDTO> updateAchievement(@PathVariable Long id,
            @RequestBody AchievementUpdateDTO dto) {
        return ResponseEntity.ok(achievementService.updateAchievement(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete achievement")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get unlocked achievements for user")
    public ResponseEntity<List<UserAchievementResponseDTO>> getUserAchievements(@PathVariable Long userId) {
        return ResponseEntity.ok(achievementService.getUserAchievements(userId));
    }

    @PostMapping("/user/{userId}/check")
    @Operation(summary = "Check and unlock eligible achievements")
    public ResponseEntity<Void> checkAchievements(@PathVariable Long userId) {
        achievementService.checkAndUnlockAchievements(userId);
        return ResponseEntity.ok().build();
    }
}
