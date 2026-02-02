package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.lesson.LessonCreateDTO;
import ma.youcode.regalis.dto.lesson.LessonResponseDTO;
import ma.youcode.regalis.dto.lesson.LessonUpdateDTO;
import ma.youcode.regalis.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
@Tag(name = "Lessons", description = "Lesson management")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    @Operation(summary = "Create a new lesson")
    public ResponseEntity<LessonResponseDTO> createLesson(@RequestBody LessonCreateDTO dto) {
        return new ResponseEntity<>(lessonService.createLesson(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lesson by ID")
    public ResponseEntity<LessonResponseDTO> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @GetMapping("/module/{moduleId}")
    @Operation(summary = "Get lessons by module ID")
    public ResponseEntity<List<LessonResponseDTO>> getLessonsByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(lessonService.getLessonsByModuleId(moduleId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update lesson")
    public ResponseEntity<LessonResponseDTO> updateLesson(@PathVariable Long id, @RequestBody LessonUpdateDTO dto) {
        return ResponseEntity.ok(lessonService.updateLesson(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete lesson")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
