package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.CategoryRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.AdminCategoryService;
import ru.practicum.explorewithme.service.CategoryService;

import java.util.List;

/**
 * Класс, ответственный за операции с категориями для Администратора
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    /**
     * Обновляет категорию
     */
    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        log.info("Получен запрос на обновление категории: " + categoryDto.getId());

        Category categoryForUpdate = categoryService.getCategory(categoryDto.getId());
        categoryMapper.updateCategoryFromDto(categoryDto, categoryForUpdate);
        Category updatedCategory = categoryRepository.save(categoryForUpdate);

        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    @Transactional
    public CategoryDto addNewCategory(CategoryDto categoryDto) {
        log.info("Получен запрос на добавление категории:" + categoryDto.getName());

        Category categoryForSave = categoryMapper.toCategory(categoryDto);

        return categoryMapper.toDto(categoryRepository.save(categoryForSave));
    }

    @Override
    public void deleteCategoryById(long categoryId) {
        log.info("Получен запрос на удаление категории id = " + categoryId);

        categoryService.getCategory(categoryId);

        List<Event> eventsWithCategory = eventRepository.findAllByCategoryId(categoryId);

        if (eventsWithCategory.size() == 0) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new ValidationException("с категорией не должно быть связано ни одного события");
        }
    }
}