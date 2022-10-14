package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CategoryDto;

/**
 * Интерфейс для сервиса категорий для Администратора
 */
public interface AdminCategoryService {
    CategoryDto updateCategory(CategoryDto categoryDto);

    CategoryDto addNewCategory(CategoryDto categoryDto);

    void deleteCategoryById(long categoryId);
}
