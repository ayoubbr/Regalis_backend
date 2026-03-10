package ma.youcode.regalis.service;

import ma.youcode.regalis.dto.category.CategoryCreateDTO;
import ma.youcode.regalis.dto.category.CategoryResponseDTO;
import ma.youcode.regalis.dto.category.CategoryUpdateDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryCreateDTO dto);

    CategoryResponseDTO getCategoryById(Long id);

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO updateCategory(Long id, CategoryUpdateDTO dto);

    void deleteCategory(Long id);
}
