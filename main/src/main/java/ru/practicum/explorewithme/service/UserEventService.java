package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.*;

import java.util.List;

/**
 * Интерфейс для сервиса событий для пользователей
 */
public interface UserEventService {
    List<EventShortDto> getAllEventsByUserId(long userId, int from, int size);

    EventFullDto patchEventByUser(UpdateEventRequestDto updateEventRequestDto, long userId);

    EventFullDto addNewEventByUser(NewEventDto newEventDto, long userId);

    EventFullDto getEventByEventIdAndUserId(long userId, long eventId);

    EventFullDto cancelEventByEventIdAndUserId(long userId, long eventId);

    List<ParticipationRequestDto> getAllParticipationRequestByUserIdAndEventId(long userId, long eventId);

    ParticipationRequestDto confirmationParticipationRequest(long userId, long eventId, long requestId,
                                                             boolean confirmation);
}
