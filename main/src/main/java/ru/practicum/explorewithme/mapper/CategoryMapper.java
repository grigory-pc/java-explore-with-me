package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.model.Category;

import java.util.List;

/**
 * Маппер между объектами Category и CategoryDto
 */
public interface CategoryMapper {
    Category toCategory(CategoryDto dto);

    CategoryDto toDto(Category category);

    List<CategoryDto> toDto(Iterable<Category> category);
}
