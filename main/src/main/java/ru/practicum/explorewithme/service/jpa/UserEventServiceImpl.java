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
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.service.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private final CategoryService categoryService;

    @Override
    public List<EventShortDto> getAllEventsByUserId(long userId, int from, int size) {
        log.info("Получен запрос на получение списка событий, добавленных пользователем: " + userId);

        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        adminUserService.getUser(userId);

        List<Event> allEvents = eventRepository.findAllByInitiatorId(userId, pageable);

        return eventMapper.toShortDto(allEvents);
    }

    @Override
    @Transactional
    public UpdateEventRequestDto patchEventByUser(UpdateEventRequestDto updateEventRequestDto, long userId) {
        log.info("Получен запрос на обновление события: " + updateEventRequestDto.getEventId() + ", добавленного" +
                " пользователем: " + userId);

        adminUserService.getUser(userId);

        Event eventForUpdateByUser = eventService.getEvent(updateEventRequestDto.getEventId());

        checkEventTime(eventForUpdateByUser);
        checkEventByInitiator(updateEventRequestDto.getEventId(), userId);

        if (eventForUpdateByUser.getState().equals(State.CANCELED)) {
            eventMapper.updateEventFromDto(updateEventRequestDto, eventForUpdateByUser);
            eventForUpdateByUser.setRequestModeration("true");
        } else if (eventForUpdateByUser.getRequestModeration().equals("true")) {
            eventMapper.updateEventFromDto(updateEventRequestDto, eventForUpdateByUser);
        } else {
            throw new ValidationException("Событие не отмененное события или в состоянии ожидания модерации");
        }

        Event updatedEvent = eventRepository.save(eventForUpdateByUser);

        return eventMapper.toDtoByUser(updatedEvent);
    }

    @Override
    @Transactional
    public NewEventDto addNewEventByUser(NewEventDto newEventDto, long userId) {
        log.info("Получен запрос на добавление события от пользователя: " + userId);

        User initiator = adminUserService.getUser(userId);
        Category category = categoryService.getCategory(newEventDto.getCategoryId());

        Event eventForSave = eventMapper.toEvent(newEventDto);
        checkEventTime(eventForSave);
        eventForSave.setCreatedOn(LocalDateTime.now());
        eventForSave.setInitiator(initiator);
        eventForSave.setCategory(category);

        return eventMapper.toNewEventDto(eventRepository.save(eventForSave));
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

        if (!eventForUpdate.getRequestModeration().equals("true")) {
            throw new ValidationException("Отменить можно только событие в состоянии ожидания модерации");
        }

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

            Event event = eventService.getEvent(eventId);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        } else {
            requestForUpdate.setStatus(Status.REJECTED);
        }
        Request updatedRequest = requestRepository.save(requestForUpdate);

        Event updatedEvent = eventService.getEvent(eventId);

        if (updatedEvent.getParticipantLimit() == updatedEvent.getConfirmedRequests()) {
            List<Request> requestToParticipantInEvent = requestRepository.findAllByEventIdAndStatus(eventId,
                    Status.PENDING);
            for (Request request : requestToParticipantInEvent) {
                request.setStatus(Status.REJECTED);
                requestRepository.save(request);
            }
        }

        return requestMapper.toDto(updatedRequest);
    }

    private void checkEventByInitiator(long eventId, long userId) {
        if (eventService.getEvent(eventId).getInitiator().getId() != userId) {
            throw new ValidationException("пользователь: " + userId + " не является инициатором события: " + eventId);
        }
    }

    private void checkEventTime(Event event) {
        long hoursOfEventDate = ChronoUnit.HOURS.between(LocalDateTime.now(), event.getEventDate());

        if (hoursOfEventDate < 2) {
            throw new ValidationException("дата и время на которые намечено событие не может быть раньше, чем через " +
                    "два часа от текущего момента");
        }
    }
}
