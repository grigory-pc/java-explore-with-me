package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.service.CategoryService;

import java.util.List;

/**
 * Основной контроллер для работы с категориями
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Возвращает список категорий
     *
     * @param from с какого id объекта начинать поиск
     * @param size максимальное количество возвращаемых записей
     * @return список объектов категорий
     */
    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(defaultValue = "0") int from,
                                              @RequestParam(defaultValue = "10") int size) {
        return categoryService.getAllCategories(from, size);
    }

    /**
     * Возвращает категорию по id
     *
     * @param catId категории
     * @return объект категории
     */
    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable long catId) {
        return categoryService.getCategoryById(catId);
    }
}