package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.adminnote.AdminNoteCreateDTO;
import ma.youcode.regalis.dto.adminnote.AdminNoteResponseDTO;
import ma.youcode.regalis.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Administrative operations")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/notes")
    @Operation(summary = "Create an admin note for a user")
    public ResponseEntity<AdminNoteResponseDTO> createNote(@RequestBody AdminNoteCreateDTO dto) {
        return new ResponseEntity<>(adminService.createAdminNote(dto), HttpStatus.CREATED);
    }

    @GetMapping("/notes/user/{userId}")
    @Operation(summary = "Get all notes for a user")
    public ResponseEntity<List<AdminNoteResponseDTO>> getNotesForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.getNotesForUser(userId));
    }

    @GetMapping("/notes")
    @Operation(summary = "Get all admin notes")
    public ResponseEntity<List<AdminNoteResponseDTO>> getAllNotes() {
        return ResponseEntity.ok(adminService.getAllNotes());
    }
}
