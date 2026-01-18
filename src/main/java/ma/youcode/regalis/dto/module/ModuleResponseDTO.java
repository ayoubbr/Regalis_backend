package ma.youcode.regalis.dto.module;

public record ModuleResponseDTO(
        Long id,
        String name,
        String description,
        Integer orderIndex) {
}
