package ma.youcode.regalis.service.impl;

import lombok.RequiredArgsConstructor;
import ma.youcode.regalis.dto.module.ModuleCreateDTO;
import ma.youcode.regalis.dto.module.ModuleResponseDTO;
import ma.youcode.regalis.dto.module.ModuleUpdateDTO;
import ma.youcode.regalis.entity.Module;
import ma.youcode.regalis.exception.EntityNotFoundException;
import ma.youcode.regalis.mapper.ModuleMapper;
import ma.youcode.regalis.repository.ModuleRepository;
import ma.youcode.regalis.service.ModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

    @Override
    public ModuleResponseDTO createModule(ModuleCreateDTO dto) {
        Module module = moduleMapper.toEntity(dto);
        Module savedModule = moduleRepository.save(module);
        return moduleMapper.toDTO(savedModule);
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleResponseDTO getModuleById(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Module not found with id: " + id));
        return moduleMapper.toDTO(module);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleResponseDTO> getAllModules() {
        return moduleRepository.findAll().stream()
                .map(moduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ModuleResponseDTO updateModule(Long id, ModuleUpdateDTO dto) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Module not found with id: " + id));
        moduleMapper.updateEntityFromDTO(dto, module);
        Module updatedModule = moduleRepository.save(module);
        return moduleMapper.toDTO(updatedModule);
    }

    @Override
    public void deleteModule(Long id) {
        if (!moduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Module not found with id: " + id);
        }
        moduleRepository.deleteById(id);
    }
}
