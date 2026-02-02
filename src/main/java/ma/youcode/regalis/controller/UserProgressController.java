package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.userprogress.UserProgressCreateDTO;
import ma.youcode.regalis.dto.userprogress.UserProgressResponseDTO;
import ma.youcode.regalis.dto.userprogress.UserProgressUpdateDTO;
import ma.youcode.regalis.service.UserProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
@Tag(name = "User Progress", description = "Track lesson progress")
public class UserProgressController {

    private final UserProgressService userProgressService;

    @PostMapping
    @Operation(summary = "Start a lesson (create progress entry)")
    public ResponseEntity<UserProgressResponseDTO> startLesson(@RequestBody UserProgressCreateDTO dto) {
        return new ResponseEntity<>(userProgressService.startLesson(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update progress (mark complete)")
    public ResponseEntity<UserProgressResponseDTO> updateProgress(@PathVariable Long id,
            @RequestBody UserProgressUpdateDTO dto) {
        return ResponseEntity.ok(userProgressService.updateProgress(id, dto));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all progress for a user")
    public ResponseEntity<List<UserProgressResponseDTO>> getUserProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(userProgressService.getUserProgress(userId));
    }

    @GetMapping("/user/{userId}/lesson/{lessonId}")
    @Operation(summary = "Get progress for specific lesson")
    public ResponseEntity<UserProgressResponseDTO> getProgressByLesson(@PathVariable Long userId,
            @PathVariable Long lessonId) {
        return ResponseEntity.ok(userProgressService.getProgressByLesson(userId, lessonId));
    }
}
