package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.service.AdminEventService;

import java.util.List;

/**
 * Основной контроллер для работы Администратора с подборками
 */
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final AdminEventService adminEventService;

    /**
     * Возвращает список событий, найденных по переданным параметрам поиска
     *
     * @param user список пользователей
     * @param state список статусов
     * @param category список категорий
     * @param rangeStart нижний диапазон даты поиска
     * @param rangeEnd верхний диапазон даты поиска
     * @param sort сортировка
     * @param from с какого id объекта начинать поиск
     * @param size максимальное количество возвращаемых записей
     * @return список объектов событий
     */
    @GetMapping
    public List<EventFullDto> searchEvents(@RequestParam List<Long> user,
                                           @RequestParam List<State> state,
                                           @RequestParam List<Long> category,
                                           @RequestParam String rangeStart,
                                           @RequestParam String rangeEnd,
                                           @RequestParam String sort,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size) {
        return adminEventService.getAllEventsByParameters(user, state, category, rangeStart, rangeEnd, sort, from, size);
    }
}
