package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.leaderboardentry.LeaderboardEntryResponseDTO;
import ma.youcode.regalis.enums.Period;
import ma.youcode.regalis.service.LeaderboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
@Tag(name = "Leaderboard", description = "Ranking and leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/{period}")
    @Operation(summary = "Get leaderboard by period (WEEKLY, MONTHLY, ALL_TIME)")
    public ResponseEntity<List<LeaderboardEntryResponseDTO>> getLeaderboard(@PathVariable Period period) {
        return ResponseEntity.ok(leaderboardService.getLeaderboard(period));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Force refresh all leaderboard periods")
    public ResponseEntity<Void> refreshAll() {
        leaderboardService.refreshAll();
        return ResponseEntity.ok().build();
    }
}
