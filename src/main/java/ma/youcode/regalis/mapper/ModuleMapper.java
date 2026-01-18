package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.module.ModuleCreateDTO;
import ma.youcode.regalis.dto.module.ModuleResponseDTO;
import ma.youcode.regalis.dto.module.ModuleUpdateDTO;
import ma.youcode.regalis.entity.Module;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ModuleMapper {
    Module toEntity(ModuleCreateDTO dto);

    ModuleResponseDTO toDTO(Module entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ModuleUpdateDTO dto, @MappingTarget Module entity);
}
