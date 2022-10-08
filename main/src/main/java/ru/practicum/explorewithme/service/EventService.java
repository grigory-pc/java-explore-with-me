package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.model.Event;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для сервисов событий
 */
public interface EventService {
    List<EventShortDto> getAllEventsByParameters(String text, List<Long> categoryId, String paid, LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd, String onlyAvailable, String sort, int from, int size);

    EventFullDto getEventById(long id);

    Event getEvent(long eventId);
}
