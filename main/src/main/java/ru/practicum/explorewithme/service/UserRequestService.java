package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Интерфейс для сервиса запросов для пользователей
 */
public interface UserRequestService {
    List<ParticipationRequestDto> getAllRequestsByRequesterId(long userId);

    ParticipationRequestDto addNewEventRequest(ParticipationRequestDto participationRequestDto, long userId);

    ParticipationRequestDto cancelEventRequest(long reqId, long userId);
}
