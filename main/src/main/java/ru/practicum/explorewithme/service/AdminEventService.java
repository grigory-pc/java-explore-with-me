package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.AdminUpdateEventRequestDto;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.State;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для сервиса событий для Администратора
 */
public interface AdminEventService {
    List<EventFullDto> getAllEventsByParameters(List<Long> userId, List<State> states, List<Long> categoryId,
                                                String rangeStart, String rangeEnd, String sort, int from, int size);

    AdminUpdateEventRequestDto updateEventByAdmin(AdminUpdateEventRequestDto adminUpdateEventRequestDto, long eventId);

    EventFullDto updateStateOfEventByAdmin(long eventId, boolean publish);
}
