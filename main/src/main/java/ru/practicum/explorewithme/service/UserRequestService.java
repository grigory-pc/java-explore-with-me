package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Интерфейс для сервиса запросов для пользователей
 */
public interface UserRequestService {
    List<ParticipationRequestDto> getAllRequestsByRequesterId(long userId);

    ParticipationRequestDto addNewEventRequest(long userId, long eventId);

    ParticipationRequestDto cancelEventRequest(long userId, long eventId);
}
