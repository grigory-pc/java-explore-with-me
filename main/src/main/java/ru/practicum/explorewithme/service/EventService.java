package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.service.jpa.SortVariant;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для сервисов событий
 */
public interface EventService {
    List<EventShortDto> getAllEventsByParameters(String text, List<Long> categoryId, boolean paid,
                                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable,
                                                 SortVariant sort, int from, int size);

    EventFullDto getEventById(long id);

    Event getEvent(long eventId);
}
