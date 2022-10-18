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
import ru.practicum.explorewithme.model.*;
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

    /**
     * Возвращает список событий, добавленных пользователем
     */
    @Override
    public List<EventShortDto> getAllEventsByUserId(long userId, int from, int size) {
        log.info("Получен запрос на получение списка событий, добавленных пользователем: " + userId);

        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        adminUserService.getUser(userId);

        List<Event> allEvents = eventRepository.findAllByInitiatorId(userId, pageable);

        return eventMapper.toShortDto(allEvents);
    }

    /**
     * Обновляет событие, добавленное пользователем
     */
    @Override
    @Transactional
    public EventFullDto patchEventByUser(UpdateEventRequestDto updateEventRequestDto, long userId) {
        log.info("Получен запрос на обновление события: " + updateEventRequestDto.getId() + ", добавленного" +
                " пользователем: " + userId);

        adminUserService.getUser(userId);
        long eventId = updateEventRequestDto.getId();

        Event eventForUpdateByUser = eventService.getEvent(eventId);

        checkEventTime(eventForUpdateByUser);
        checkEventByInitiator(updateEventRequestDto.getId(), userId);

        if (eventForUpdateByUser.getState().equals(State.CANCELED)) {
            eventMapper.updateEventFromDto(updateEventRequestDto, eventForUpdateByUser);
            eventForUpdateByUser.setRequestModeration(true);
        } else if (eventForUpdateByUser.isRequestModeration()) {
            eventMapper.updateEventFromDto(updateEventRequestDto, eventForUpdateByUser);
        } else {
            throw new ValidationException("Изменить можно только отмененные события или события в состоянии ожидания" +
                    " модерации");
        }

        Event updatedEvent = eventRepository.save(eventForUpdateByUser);
        EventFullDto eventFullDto = eventMapper.toFullDto(updatedEvent);

        return eventFullDto;
    }

    /**
     * Добавляет новое событие
     */
    @Override
    @Transactional
    public EventFullDto addNewEventByUser(NewEventDto newEventDto, long userId) {
        log.info("Получен запрос на добавление события от пользователя: " + userId);

        User initiator = adminUserService.getUser(userId);
        Category category = categoryService.getCategory(newEventDto.getCategoryId());

        Event eventForSave = eventMapper.toEvent(newEventDto);

        checkEventTime(eventForSave);

        eventForSave.setCreatedOn(LocalDateTime.now());
        eventForSave.setInitiator(initiator);
        eventForSave.setCategory(category);
        eventForSave.setState(State.PENDING);

        return eventMapper.toFullDto(eventRepository.save(eventForSave));
    }

    /**
     * Возвращает полную информацию о событии по id, которое было добавлено пользователем
     */
    @Override
    public EventFullDto getEventByEventIdAndUserId(long userId, long eventId) {
        log.info("Получен запрос на получение информации о событии: " + eventId + " от пользователя: " + userId);

        adminUserService.getUser(userId);
        Event event = eventService.getEvent(eventId);
        checkEventByInitiator(eventId, userId);

        EventFullDto eventFullDto = eventMapper.toFullDto(event);
        int views = eventService.getEventViews(eventId);
        eventFullDto.setViews(views);

        return eventFullDto;
    }

    /**
     * Отменяет событие, добавленное пользователем
     */
    @Override
    @Transactional
    public EventFullDto cancelEventByEventIdAndUserId(long userId, long eventId) {
        log.info("Получен запрос на отмену события: " + eventId + " от пользователя: " + userId);

        adminUserService.getUser(userId);
        eventService.getEvent(eventId);
        checkEventByInitiator(eventId, userId);

        Event eventForUpdate = eventService.getEvent(eventId);


        if (!eventForUpdate.isRequestModeration()) {
            throw new ValidationException("Отменить можно только событие в состоянии ожидания модерации");
        }

        eventForUpdate.setState(State.CANCELED);
        Event updatedEvent = eventRepository.save(eventForUpdate);

        EventFullDto eventFullDto = eventMapper.toFullDto(updatedEvent);
        int views = eventService.getEventViews(eventId);
        eventFullDto.setViews(views);

        return eventFullDto;
    }

    /**
     * Возвращает список запросов на событие пользователя
     */
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

    /**
     * Подтверждает или отклоняет запрос на участие в событии пользователя
     */
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