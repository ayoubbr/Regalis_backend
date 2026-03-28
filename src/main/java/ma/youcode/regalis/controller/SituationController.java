package ma.youcode.regalis.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.situation.SituationCreateDTO;
import ma.youcode.regalis.dto.situation.SituationResponseDTO;
import ma.youcode.regalis.dto.situation.SituationUpdateDTO;
import ma.youcode.regalis.service.SituationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/situations")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class SituationController {

    private final SituationService situationService;

    @PostMapping
    public ResponseEntity<SituationResponseDTO> create(@Valid @RequestBody SituationCreateDTO dto) {
        return new ResponseEntity<>(situationService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SituationResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SituationUpdateDTO dto) {
        return ResponseEntity.ok(situationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        situationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/puzzle/{puzzleId}")
    public ResponseEntity<List<SituationResponseDTO>> getByPuzzle(@PathVariable Long puzzleId) {
        return ResponseEntity.ok(situationService.getByPuzzleId(puzzleId));
    }
}
