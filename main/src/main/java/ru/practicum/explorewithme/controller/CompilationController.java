package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.service.CompilationService;

import java.util.List;

/**
 * Основной контроллер для работы с подборками
 */
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;

    /**
     * Возвращает список подборок
     *
     * @param pinned определение поиска: закрепленные или незакрепленные подборки
     * @param from   с какого id объекта начинать поиск
     * @param size   максимальное количество возвращаемых записей
     * @return список объектов подборок
     */
    @GetMapping
    public List<CompilationDto> getAllCompilations(@RequestParam String pinned,
                                                   @RequestParam(defaultValue = "0") int from,
                                                   @RequestParam(defaultValue = "10") int size) {
        return compilationService.getAllPinnedCompilations(pinned, from, size);
    }

    /**
     * Возвращает подборку по id
     *
     * @param compId подборки
     * @return объект подборки
     */
    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable long compId) {
        return compilationService.getCompilationById(compId);
    }
}