package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.question.QuestionCreateDTO;
import ma.youcode.regalis.dto.question.QuestionResponseDTO;
import ma.youcode.regalis.dto.question.QuestionUpdateDTO;
import ma.youcode.regalis.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Tag(name = "Questions", description = "Question management for quizzes")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    @Operation(summary = "Create a new question")
    public ResponseEntity<QuestionResponseDTO> createQuestion(@RequestBody QuestionCreateDTO dto) {
        return new ResponseEntity<>(questionService.createQuestion(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get question by ID")
    public ResponseEntity<QuestionResponseDTO> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @GetMapping("/quiz/{quizId}")
    @Operation(summary = "Get all questions for a specific quiz")
    public ResponseEntity<List<QuestionResponseDTO>> getQuestionsByQuizId(@PathVariable Long quizId) {
        return ResponseEntity.ok(questionService.getQuestionsByQuizId(quizId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update question")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionUpdateDTO dto) {
        return ResponseEntity.ok(questionService.updateQuestion(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete question")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
