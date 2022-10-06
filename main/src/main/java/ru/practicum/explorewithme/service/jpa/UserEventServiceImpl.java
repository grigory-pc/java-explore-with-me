package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.repository.UserRepository;
import ru.practicum.explorewithme.service.UserEventService;

import java.util.List;

/**
 * Класс, ответственный за операции с событиями для пользователя
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserEventServiceImpl implements UserEventService {
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Override
    public List<EventShortDto> getAllEventsByUserId(long userId, int from, int size) {
        return null;
    }

    @Override
    @Transactional
    public UpdateEventRequestDto patchEventByUser(UpdateEventRequestDto updateEventRequestDto, long userId) {
        return null;
    }

    @Override
    @Transactional
    public NewEventDto addNewEventByUser(NewEventDto newEventDto, long userId) {
        return null;
    }

    @Override
    public EventFullDto getEventByEventIdAndUserId(long userId, long eventId) {
        return null;
    }

    @Override
    @Transactional
    public EventFullDto cancelEventByEventIdAndUserId(long userId, long eventId) {
        return null;
    }

    @Override
    @Transactional
    public List<ParticipationRequestDto> getAllParticipationRequestByUserIdAndEventId(long userId, long eventId) {
        return null;
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmationParticipationRequest(long userId, long eventId, long requestId,
                                                              boolean confirmation) {
        return null;
    }
}
