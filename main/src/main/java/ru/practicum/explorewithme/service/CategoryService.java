package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.model.Category;

import java.util.List;

/**
 * Интерфейс для сервисов категорий
 */
public interface CategoryService {
    List<CategoryDto> getAllCategories(int from, int size);

    CategoryDto getCategoryById(long id);

    Category getCategory(long categoryId);
}
