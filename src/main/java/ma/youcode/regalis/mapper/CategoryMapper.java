package ma.youcode.regalis.mapper;

import ma.youcode.regalis.dto.category.CategoryCreateDTO;
import ma.youcode.regalis.dto.category.CategoryResponseDTO;
import ma.youcode.regalis.dto.category.CategoryUpdateDTO;
import ma.youcode.regalis.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "puzzles", ignore = true)
    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryCreateDTO dto);

    CategoryResponseDTO toDTO(Category entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "puzzles", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CategoryUpdateDTO dto, @MappingTarget Category entity);
}
