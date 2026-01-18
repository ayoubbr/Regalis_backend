package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.module.ModuleCreateDTO;
import ma.youcode.regalis.dto.module.ModuleResponseDTO;
import ma.youcode.regalis.dto.module.ModuleUpdateDTO;
import java.util.List;

public interface ModuleService {
    ModuleResponseDTO createModule(ModuleCreateDTO dto);

    ModuleResponseDTO getModuleById(Long id);

    List<ModuleResponseDTO> getAllModules();

    ModuleResponseDTO updateModule(Long id, ModuleUpdateDTO dto);

    void deleteModule(Long id);
}
