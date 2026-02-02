package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.dailychallenge.DailyChallengeCreateDTO;
import ma.youcode.regalis.dto.dailychallenge.DailyChallengeResponseDTO;
import ma.youcode.regalis.dto.dailychallenge.DailyChallengeUpdateDTO;
import ma.youcode.regalis.service.DailyChallengeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/daily-challenges")
@RequiredArgsConstructor
@Tag(name = "Daily Challenges", description = "Daily puzzle challenges")
public class DailyChallengeController {

    private final DailyChallengeService dailyChallengeService;

    @PostMapping
    @Operation(summary = "Create a daily challenge")
    public ResponseEntity<DailyChallengeResponseDTO> createChallenge(@RequestBody DailyChallengeCreateDTO dto) {
        return new ResponseEntity<>(dailyChallengeService.createDailyChallenge(dto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/today")
    @Operation(summary = "Get today's challenge for user")
    public ResponseEntity<DailyChallengeResponseDTO> getTodayChallenge(@PathVariable Long userId) {
        return ResponseEntity.ok(dailyChallengeService.getDailyChallengeForUser(userId, LocalDate.now()));
    }

    @GetMapping("/user/{userId}/date/{date}")
    @Operation(summary = "Get challenge for user on specific date")
    public ResponseEntity<DailyChallengeResponseDTO> getChallengeByDate(@PathVariable Long userId,
            @PathVariable LocalDate date) {
        return ResponseEntity.ok(dailyChallengeService.getDailyChallengeForUser(userId, date));
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "Mark challenge as completed")
    public ResponseEntity<DailyChallengeResponseDTO> completeChallenge(@PathVariable Long id,
            @RequestBody DailyChallengeUpdateDTO dto) {
        return ResponseEntity.ok(dailyChallengeService.markChallengeCompleted(id, dto));
    }

    @GetMapping("/user/{userId}/history")
    @Operation(summary = "Get user's challenge history")
    public ResponseEntity<List<DailyChallengeResponseDTO>> getChallengeHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(dailyChallengeService.getUserChallengeHistory(userId));
    }
}
