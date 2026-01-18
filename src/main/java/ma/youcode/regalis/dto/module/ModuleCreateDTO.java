package ma.youcode.regalis.dto.module;

public record ModuleCreateDTO(
        String name,
        String description,
        Integer orderIndex) {
}
