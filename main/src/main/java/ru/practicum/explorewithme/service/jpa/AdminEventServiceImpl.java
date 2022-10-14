package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetBasedPageRequest;
import ru.practicum.explorewithme.dto.AdminUpdateEventRequestDto;
import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.mapper.CommentMapper;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.CommentRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.AdminEventService;
import ru.practicum.explorewithme.service.EventService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Класс, ответственный за операции с событиями для Администратора
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final EventMapper eventMapper;
    private final CommentMapper commentMapper;
    private final EventService eventService;

    /**
     * Возвращает список событий, найденных по параметрам запроса
     */
    @Override
    public List<EventFullDto> getAllEventsByParameters(List<Long> userIds, List<State> states, List<Long> categoryIds,
                                                       LocalDateTime rangeStart, LocalDateTime rangeEnd, int from,
                                                       int size) {
        log.info("Получен запрос на получение списка событий");

        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        List<Event> allEvents = eventRepository
                .findAllByInitiatorIdInAndStateInAndAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
                        userIds, states, categoryIds, rangeStart, rangeEnd, pageable);

        List<EventFullDto> allEventsFullDto = eventMapper.toFullDto(allEvents);

        for (EventFullDto eventFullDto : allEventsFullDto) {
            int views = eventService.getEventViews(eventFullDto.getId());
            eventFullDto.setViews(views);

            List<CommentDto> existEventComments = commentMapper.toDto(commentRepository.
                    findAllByEventId(eventFullDto.getId()));
            eventFullDto.setComments(existEventComments);
        }

        return allEventsFullDto;
    }

    /**
     * Обновляет событие по запросу от Администратора
     */
    @Override
    @Transactional
    public AdminUpdateEventRequestDto updateEventByAdmin(AdminUpdateEventRequestDto adminUpdateEventRequestDto,
                                                         long eventId) {
        log.info("Получен запрос от администратора на обновление события  " + adminUpdateEventRequestDto.getTitle());

        Event eventForUpdateByAdmin = eventService.getEvent(eventId);
        eventMapper.adminUpdateEventFromDto(adminUpdateEventRequestDto, eventForUpdateByAdmin);
        Event updatedEvent = eventRepository.save(eventForUpdateByAdmin);

        return eventMapper.toDtoByAdmin(updatedEvent);
    }

    /**
     * Обновляет статус публикации события
     */
    @Override
    @Transactional
    public EventFullDto updateStateOfEventByAdmin(long eventId, boolean publish) {
        log.info("Получен запрос от администратора на обновление статуса публикации для события: " + eventId);

        Event eventForUpdate = eventService.getEvent(eventId);

        if (publish && eventForUpdate.getState().equals(State.PENDING)) {
            checkEventTime(eventForUpdate);

            eventForUpdate.setState(State.PUBLISHED);
            eventForUpdate.setPublishedOn(LocalDateTime.now());
        } else if (!eventForUpdate.getState().equals(State.PUBLISHED)) {
            eventForUpdate.setState(State.CANCELED);
        }
        Event updatedEvent = eventRepository.save(eventForUpdate);

        EventFullDto updatedEventFullDto = eventMapper.toFullDto(updatedEvent);
        int views = eventService.getEventViews(eventId);
        updatedEventFullDto.setViews(views);

        List<CommentDto> existEventComments = commentMapper.toDto(commentRepository.findAllByEventId(eventId));
        updatedEventFullDto.setComments(existEventComments);

        return updatedEventFullDto;
    }

    private void checkEventTime(Event event) {
        long hoursOfEventDate = ChronoUnit.HOURS.between(LocalDateTime.now(), event.getEventDate());

        if (hoursOfEventDate < 1) {
            throw new ValidationException("дата начала события должна быть не ранее чем за час от даты публикации");
        }
    }
}