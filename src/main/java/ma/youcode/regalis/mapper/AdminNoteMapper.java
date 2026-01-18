package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.adminnote.AdminNoteCreateDTO;
import ma.youcode.regalis.dto.adminnote.AdminNoteResponseDTO;
import ma.youcode.regalis.dto.adminnote.AdminNoteUpdateDTO;
import ma.youcode.regalis.entity.AdminNote;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AdminNoteMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AdminNote toEntity(AdminNoteCreateDTO dto);

    @Mapping(source = "user.id", target = "userId")
    AdminNoteResponseDTO toDTO(AdminNote entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    void updateEntityFromDTO(AdminNoteUpdateDTO dto, @MappingTarget AdminNote entity);
}
