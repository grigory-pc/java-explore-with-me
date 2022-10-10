package ru.practicum.explorewithmestat.service;

import ru.practicum.explorewithmestat.dto.EndpointHitDto;
import ru.practicum.explorewithmestat.dto.ViewStatsDto;
import ru.practicum.explorewithmestat.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для сервиса статистики
 */
public interface EndpointHitService {
    void addStats (EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats (LocalDateTime start, LocalDateTime end, List<String> uris, String unique);
}
