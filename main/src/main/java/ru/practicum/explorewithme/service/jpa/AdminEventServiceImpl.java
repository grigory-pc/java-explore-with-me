package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetBasedPageRequest;
import ru.practicum.explorewithme.dto.AdminUpdateEventRequestDto;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Event;
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
    private final EventMapper eventMapper;
    private final EventService eventService;

    @Override
    public List<EventFullDto> getAllEventsByParameters(List<Long> userIdList, List<State> states, List<Long> categoryId,
                                                       LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        log.info("Получен запрос на получение списка событий");

        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        List<Event> allEvents = eventRepository
                .findAllByInitiatorIdInAndStateInAndAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
                        userIdList, states, categoryId, rangeStart, rangeEnd, pageable);

        return eventMapper.toFullDto(allEvents);
    }

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

        return eventMapper.toFullDto(updatedEvent);
    }

    private void checkEventTime(Event event) {
        long hoursOfEventDate = ChronoUnit.HOURS.between(event.getEventDate(), LocalDateTime.now());

        if (hoursOfEventDate < 1) {
            throw new ValidationException("дата начала события должна быть не ранее чем за час от даты публикации");
        }
    }
}