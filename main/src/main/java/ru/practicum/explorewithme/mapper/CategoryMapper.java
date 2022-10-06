package ru.practicum.explorewithme.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.dto.UpdateEventRequestDto;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Event;

import java.util.List;

/**
 * Маппер между объектами Category и CategoryDto
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryDto dto);

    CategoryDto toDto(Category category);

    List<CategoryDto> toDto(Iterable<Category> category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromDto(CategoryDto categoryDto, @MappingTarget Category category);
}
