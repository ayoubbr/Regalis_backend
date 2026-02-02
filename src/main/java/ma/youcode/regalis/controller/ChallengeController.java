package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.challenge.ChallengeCreateDTO;
import ma.youcode.regalis.dto.challenge.ChallengeResponseDTO;
import ma.youcode.regalis.dto.challenge.ChallengeUpdateDTO;
import ma.youcode.regalis.service.ChallengeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
@Tag(name = "Challenges", description = "User vs User challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    @Operation(summary = "Create a challenge between users")
    public ResponseEntity<ChallengeResponseDTO> createChallenge(@RequestBody ChallengeCreateDTO dto) {
        return new ResponseEntity<>(challengeService.createChallenge(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get challenge by ID")
    public ResponseEntity<ChallengeResponseDTO> getChallengeById(@PathVariable Long id) {
        return ResponseEntity.ok(challengeService.getChallengeById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update challenge status")
    public ResponseEntity<ChallengeResponseDTO> updateChallengeStatus(@PathVariable Long id,
            @RequestBody ChallengeUpdateDTO dto) {
        return ResponseEntity.ok(challengeService.updateChallengeStatus(id, dto));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all challenges for a user")
    public ResponseEntity<List<ChallengeResponseDTO>> getChallengesForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(challengeService.getChallengesForUser(userId));
    }
}
