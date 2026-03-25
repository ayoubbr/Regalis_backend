package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.userquiz.UserQuizCreateDTO;
import ma.youcode.regalis.dto.userquiz.UserQuizResponseDTO;
import ma.youcode.regalis.dto.userquiz.UserQuizUpdateDTO;
import ma.youcode.regalis.service.UserQuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
@Tag(name = "User Quiz", description = "Track quiz progress")
public class UserQuizController {

    private final UserQuizService userQuizService;

    @PostMapping
    @Operation(summary = "Start a quiz (create progress entry)")
    public ResponseEntity<UserQuizResponseDTO> startQuiz(@RequestBody UserQuizCreateDTO dto) {
        return new ResponseEntity<>(userQuizService.startQuiz(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update progress (mark complete)")
    public ResponseEntity<UserQuizResponseDTO> updateProgress(@PathVariable Long id,
                                                              @RequestBody UserQuizUpdateDTO dto) {
        return ResponseEntity.ok(userQuizService.updateProgress(id, dto));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all progress for a user")
    public ResponseEntity<List<UserQuizResponseDTO>> getUserQuiz(@PathVariable Long userId) {
        return ResponseEntity.ok(userQuizService.getUserQuiz(userId));
    }

    @GetMapping("/user/{userId}/quiz/{quizId}")
    @Operation(summary = "Get progress for specific quiz")
    public ResponseEntity<UserQuizResponseDTO> getProgressByQuiz(@PathVariable Long userId,
                                                                   @PathVariable Long quizId) {
        return ResponseEntity.ok(userQuizService.getProgressByQuiz(userId, quizId));
    }
}
