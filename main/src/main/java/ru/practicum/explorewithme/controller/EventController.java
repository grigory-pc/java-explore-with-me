package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.client.EventClient;
import ru.practicum.explorewithme.dto.EndpointHit;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.jpa.SortVariant;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Основной контроллер для работы с событиями
 */
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventClient eventClient;

    /**
     * Возвращает список событий, найденных по переданным параметрам поиска
     *
     * @param text          текст для поиска в содержимом аннотации и подробном описании события
     * @param categories    список идентификаторов категорий в которых будет вестись поиск
     * @param paid          поиск только платных/бесплатных событий
     * @param rangeStart    нижний диапазон даты поиска
     * @param rangeEnd      верхний диапазон даты поиска
     * @param onlyAvailable только события у которых не исчерпан лимит запросов на участие
     * @param sort          сортировка
     * @param from          с какого id объекта начинать поиск
     * @param size          максимальное количество возвращаемых записей
     * @return список объектов событий
     */
    @GetMapping
    public List<EventShortDto> getAllEventsByParameters(@RequestParam String text,
                                                        @RequestParam List<Long> categories,
                                                        @RequestParam String paid,
                                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime
                                                                rangeStart,
                                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime
                                                                rangeEnd,
                                                        @RequestParam(defaultValue = "false") String onlyAvailable,
                                                        @RequestParam SortVariant sort,
                                                        @RequestParam(defaultValue = "0") int from,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        HttpServletRequest request) {

        EndpointHit endpointHit = EndpointHit.builder()
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();

        eventClient.postStat(endpointHit);

        return eventService.getAllEventsByParameters(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                from, size);
    }

    /**
     * Возвращает событие по id
     *
     * @param id подборки
     * @return объект подборки
     */
    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable long id, HttpServletRequest request) {
        EndpointHit endpointHit = EndpointHit.builder()
                .app("ewm-main-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();

        eventClient.postStat(endpointHit);

        return eventService.getEventById(id);
    }
}