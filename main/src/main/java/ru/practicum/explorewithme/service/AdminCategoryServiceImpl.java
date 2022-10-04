package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.repository.CategoryRepository;

/**
 * Класс, ответственный за операции с категориями для Администратора
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * Обновляет категорию
     */
    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        log.info("Получен запрос на обновление категории: " + categoryDto.getId());

        Category categoryForUpdate = categoryRepository.findById(categoryDto.getId());
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

        categoryRepository.deleteById(categoryId);
    }
}
