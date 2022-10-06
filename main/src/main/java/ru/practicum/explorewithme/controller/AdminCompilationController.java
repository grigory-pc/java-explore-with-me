package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.service.AdminCompilationService;

import javax.validation.Valid;

/**
 * Основной контроллер для работы Администратора с подборками
 */
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    /**
     * Добавление новой подборки Администратором
     *
     * @return возвращает добавленный объект подборки с id
     */
    @PostMapping
    public CompilationDto addNewCompilation(@Valid @RequestBody CompilationDto compilationDto) {
        return adminCompilationService.addNewCompilation(compilationDto);
    }

    /**
     * Удаление подборки Администратором
     *
     * @param compId объекта подборки
     */
    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        adminCompilationService.deleteCompilationById(compId);
    }

    /**
     * Удаление события из подборки Администратором
     *
     * @param compId  объекта подборки
     * @param eventId объекта события
     */
    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable long compId,
                                           @PathVariable long eventId) {
        adminCompilationService.deleteEventByIdFromCompilation(compId, eventId);
    }

    /**
     * Добавление события в подборку Администратором
     *
     * @param compId  объекта подборки
     * @param eventId объекта события
     * @return возвращает обновленный объект категории
     */
    @PatchMapping
    public void addEventToCompilation(@PathVariable long compId,
                                      @PathVariable long eventId) {
        adminCompilationService.addEventIdToCompilation(compId, eventId);
    }

    /**
     * Открепление подборки Администратором
     *
     * @param compId объекта подборки
     */
    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable long compId) {
        adminCompilationService.updatePinnedOfCompilation(compId, false);
    }

    /**
     * Закрепление подборки Администратором
     *
     * @param compId объекта подборки
     */
    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable long compId) {
        adminCompilationService.updatePinnedOfCompilation(compId, true);
    }
}