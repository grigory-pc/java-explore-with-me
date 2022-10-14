package ru.practicum.explorewithmestat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmestat.dto.EndpointHitDto;
import ru.practicum.explorewithmestat.dto.ViewStatsDto;
import ru.practicum.explorewithmestat.service.EndpointHitService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Основной контроллер для работы со статистикой
 */
@RestController
@RequiredArgsConstructor
public class EndpointHitController {
    private final EndpointHitService endpointHitService;

    /**
     * Добавление статистики
     */
    @PostMapping("/hit")
    public void addStats(@RequestBody EndpointHitDto endpointHitDto) {
        endpointHitService.addStats(endpointHitDto);
    }

    /**
     * Возвращает статистику, найденную по запрашиваемым параметрам
     *
     * @param start  Дата и время начала диапазона за который нужно выгрузить статистику
     * @param end    Дата и время конца диапазона за который нужно выгрузить статистику
     * @param uris   Список uri для которых нужно выгрузить статистику
     * @param unique Нужно ли учитывать только уникальные посещения (только с уникальным ip)
     * @return возвращает список статистики
     */
    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime
                                               start,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime
                                               end,
                                       @RequestParam List<String> uris,
                                       @RequestParam(defaultValue = "false") boolean unique) {
        return endpointHitService.getStats(start, end, uris, unique);
    }

    /**
     * Возвращает статистику, найденную для определенного события
     *
     * @param uri для которого нужно выгрузить статистику
     * @return возвращает количество просмотров
     */
    @GetMapping("/stats/hits")
    public int getStatsByUri(@RequestParam String uri) {
        return endpointHitService.getStatsByUri(uri);
    }
}