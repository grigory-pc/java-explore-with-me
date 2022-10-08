package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetBasedPageRequest;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.mapper.RequestMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.service.*;

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
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;
    private final AdminUserService adminUserService;
    private final EventService eventService;
    private final UserRequestService userRequestService;

    @Override
    public List<EventShortDto> getAllEventsByUserId(long userId, int from, int size) {
        log.info("Получен запрос на получение списка событий, добавленных пользователем: " + userId);

        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        adminUserService.getUser(userId);

        List<Event> allEvents = eventRepository.findByInitiatorId(userId, pageable);

        return eventMapper.toShortDto(allEvents);
    }

    @Override
    @Transactional
    public UpdateEventRequestDto patchEventByUser(UpdateEventRequestDto updateEventRequestDto, long userId) {
        log.info("Получен запрос на обновление события: " + updateEventRequestDto.getEventId() + ", добавленного" +
                " пользователем: " + userId);

        adminUserService.getUser(userId);

        Event eventForUpdateByUser = eventService.getEvent(updateEventRequestDto.getEventId());

        checkEventByInitiator(updateEventRequestDto.getEventId(), userId);
        eventMapper.updateEventFromDto(updateEventRequestDto, eventForUpdateByUser);

        Event updatedEvent = eventRepository.save(eventForUpdateByUser);

        return eventMapper.toDtoByUser(updatedEvent);
    }

    @Override
    @Transactional
    public NewEventDto addNewEventByUser(NewEventDto newEventDto, long userId) {
        log.info("Получен запрос на добавление события от пользователя: " + userId);

        adminUserService.getUser(userId);

        Event newEvent = eventRepository.save(eventMapper.toEvent(newEventDto));

        return eventMapper.toNewEvent(newEvent);
    }

    @Override
    public EventFullDto getEventByEventIdAndUserId(long userId, long eventId) {
        log.info("Получен запрос на получение информации о событии: " + eventId + " от пользователя: " + userId);

        adminUserService.getUser(userId);
        eventService.getEvent(eventId);
        checkEventByInitiator(eventId, userId);

        return eventMapper.toFullDto(eventService.getEvent(eventId));
    }

    @Override
    @Transactional
    public EventFullDto cancelEventByEventIdAndUserId(long userId, long eventId) {
        log.info("Получен запрос на отмену события: " + eventId + " от пользователя: " + userId);

        adminUserService.getUser(userId);
        eventService.getEvent(eventId);
        checkEventByInitiator(eventId, userId);

        Event eventForUpdate = eventService.getEvent(eventId);
        eventForUpdate.setState(State.CANCELED);
        Event updatedEvent = eventRepository.save(eventForUpdate);

        return eventMapper.toFullDto(updatedEvent);
    }

    @Override
    @Transactional
    public List<ParticipationRequestDto> getAllParticipationRequestByUserIdAndEventId(long userId, long eventId) {
        log.info("Получен запрос на получение всех запросов на событие: " + eventId + " пользователя: " + userId);

        adminUserService.getUser(userId);
        eventService.getEvent(eventId);
        checkEventByInitiator(eventId, userId);

        List<Request> allRequests = requestRepository.findAllByEventId(eventId);

        return requestMapper.toDto(allRequests);
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmationParticipationRequest(long userId, long eventId, long requestId,
                                                                    boolean confirmation) {
        adminUserService.getUser(userId);
        eventService.getEvent(eventId);
        checkEventByInitiator(eventId, userId);

        Request requestForUpdate = userRequestService.getRequest(requestId);
        if (confirmation) {
            requestForUpdate.setStatus(Status.CONFIRMED);
        } else {
            requestForUpdate.setStatus(Status.NOT_CONFIRMED);
        }
        Request updatedRequest = requestRepository.save(requestForUpdate);

        return requestMapper.toDto(updatedRequest);
    }

    private boolean checkEventByInitiator(long eventId, long userId) {
        if (eventService.getEvent(eventId).getInitiator().getId() == userId) {
            return true;
        } else {
            throw new ValidationException("пользователь: " + userId + " не является инициатором события: " + eventId);
        }
    }
}
