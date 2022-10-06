package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.service.AdminCategoryService;

import javax.validation.Valid;

/**
 * Основной контроллер для работы Администратора с категориями
 */
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    /**
     * Изменение категории Администратором
     *
     * @return возвращает обновленный объект категории
     */
    @PatchMapping
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return adminCategoryService.updateCategory(categoryDto);
    }

    /**
     * Добавление новой категории Администратором
     *
     * @return возвращает добавленный объект категории с id
     */
    @PostMapping
    public CategoryDto addNewCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return adminCategoryService.addNewCategory(categoryDto);
    }

    /**
     * Удаление категории Администратором
     *
     * @param catId объекта категории
     */
    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable long catId) {
        adminCategoryService.deleteCategoryById(catId);
    }
}