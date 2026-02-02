package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.puzzle.PuzzleCreateDTO;
import ma.youcode.regalis.dto.puzzle.PuzzleResponseDTO;
import ma.youcode.regalis.dto.puzzle.PuzzleUpdateDTO;
import ma.youcode.regalis.service.PuzzleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/puzzles")
@RequiredArgsConstructor
@Tag(name = "Puzzles", description = "Chess puzzle management")
public class PuzzleController {

    private final PuzzleService puzzleService;

    @PostMapping
    @Operation(summary = "Create a new puzzle")
    public ResponseEntity<PuzzleResponseDTO> createPuzzle(@RequestBody PuzzleCreateDTO dto) {
        return new ResponseEntity<>(puzzleService.createPuzzle(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get puzzle by ID")
    public ResponseEntity<PuzzleResponseDTO> getPuzzleById(@PathVariable Long id) {
        return ResponseEntity.ok(puzzleService.getPuzzleById(id));
    }

    @GetMapping("/module/{moduleId}")
    @Operation(summary = "Get puzzles by module ID")
    public ResponseEntity<List<PuzzleResponseDTO>> getPuzzlesByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(puzzleService.getPuzzlesByModuleId(moduleId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update puzzle")
    public ResponseEntity<PuzzleResponseDTO> updatePuzzle(@PathVariable Long id, @RequestBody PuzzleUpdateDTO dto) {
        return ResponseEntity.ok(puzzleService.updatePuzzle(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete puzzle")
    public ResponseEntity<Void> deletePuzzle(@PathVariable Long id) {
        puzzleService.deletePuzzle(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/verify")
    @Operation(summary = "Verify puzzle solution")
    public ResponseEntity<Map<String, Boolean>> verifySolution(@PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String submittedMoves = body.get("moves");
        boolean isCorrect = puzzleService.verifySolution(id, submittedMoves);
        return ResponseEntity.ok(Map.of("correct", isCorrect));
    }
}
