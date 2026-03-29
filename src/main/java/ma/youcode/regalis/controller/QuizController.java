package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.common.PaginatedResponse;
import ma.youcode.regalis.dto.quiz.QuizCreateDTO;
import ma.youcode.regalis.dto.quiz.QuizResponseDTO;
import ma.youcode.regalis.dto.quiz.QuizUpdateDTO;
import ma.youcode.regalis.service.QuizService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
@Tag(name = "Quizzes", description = "Quiz management")
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    @Operation(summary = "Create a new quiz")
    public ResponseEntity<QuizResponseDTO> createQuiz(@RequestBody QuizCreateDTO dto) {
        return new ResponseEntity<>(quizService.createQuiz(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get quiz by ID")
    public ResponseEntity<QuizResponseDTO> getQuizById(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @GetMapping("/module/{moduleId}")
    @Operation(summary = "Get quizzes by module ID")
    public ResponseEntity<List<QuizResponseDTO>> getQuizzesByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(quizService.getQuizzesByModuleId(moduleId));
    }

    @GetMapping
    @Operation(summary = "Get all quizzes")
    public ResponseEntity<List<QuizResponseDTO>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @GetMapping("/paged")
    @Operation(summary = "Get all quizzes with pagination and filters")
    public ResponseEntity<PaginatedResponse<QuizResponseDTO>> getPagedQuizzes(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long moduleId,
            Pageable pageable) {
        Page<QuizResponseDTO> page = quizService.getPagedQuizzes(search, moduleId, pageable);
        return ResponseEntity.ok(new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update quiz")
    public ResponseEntity<QuizResponseDTO> updateQuiz(@PathVariable Long id, @RequestBody QuizUpdateDTO dto) {
        return ResponseEntity.ok(quizService.updateQuiz(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete quiz")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }
}
