package ma.youcode.regalis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.module.ModuleCreateDTO;
import ma.youcode.regalis.dto.module.ModuleResponseDTO;
import ma.youcode.regalis.dto.module.ModuleUpdateDTO;
import ma.youcode.regalis.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
@Tag(name = "Modules", description = "Learning module management")
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping
    @Operation(summary = "Create a new module")
    public ResponseEntity<ModuleResponseDTO> createModule(@RequestBody ModuleCreateDTO dto) {
        return new ResponseEntity<>(moduleService.createModule(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get module by ID")
    public ResponseEntity<ModuleResponseDTO> getModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.getModuleById(id));
    }

    @GetMapping
    @Operation(summary = "Get all modules")
    public ResponseEntity<List<ModuleResponseDTO>> getAllModules() {
        return ResponseEntity.ok(moduleService.getAllModules());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update module")
    public ResponseEntity<ModuleResponseDTO> updateModule(@PathVariable Long id, @RequestBody ModuleUpdateDTO dto) {
        return ResponseEntity.ok(moduleService.updateModule(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete module")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
}
