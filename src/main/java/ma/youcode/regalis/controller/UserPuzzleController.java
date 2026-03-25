package ma.youcode.regalis.controller;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.userpuzzle.UserPuzzleCreateDTO;
import ma.youcode.regalis.dto.userpuzzle.UserPuzzleResponseDTO;
import ma.youcode.regalis.service.UserPuzzleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-puzzles")
@RequiredArgsConstructor
public class UserPuzzleController {

    private final UserPuzzleService userPuzzleService;

    @PostMapping
    public ResponseEntity<UserPuzzleResponseDTO> recordAttempt(@RequestBody UserPuzzleCreateDTO dto) {
        return ResponseEntity.ok(userPuzzleService.recordAttempt(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPuzzleResponseDTO>> getUserAttempts(@PathVariable Long userId) {
        return ResponseEntity.ok(userPuzzleService.getUserAttempts(userId));
    }
}
