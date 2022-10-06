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
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.AdminEventService;

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

    @Override
    public List<EventFullDto> getAllEventsByParameters(List<Long> userIdList, List<State> states, List<Long> categoryId,
                                                       String rangeStart, String rangeEnd, int from, int size) {
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

        Event eventForUpdateByAdmin = getEvent(eventId);
        eventMapper.adminUpdateEventFromDto(adminUpdateEventRequestDto, eventForUpdateByAdmin);
        Event updatedEvent = eventRepository.save(eventForUpdateByAdmin);

        return eventMapper.toDto(updatedEvent);
    }

    @Override
    @Transactional
    public EventFullDto updateStateOfEventByAdmin(long eventId, boolean publish) {
        log.info("Получен запрос от администратора на обновление статуса публикации для события: " + eventId);

        Event eventForUpdate = getEvent(eventId);

        if (publish) {
            eventForUpdate.setState(State.PUBLISHED);
        } else {
            eventForUpdate.setState(State.NOT_PUBLISHED);
        }
        Event updatedEvent = eventRepository.save(eventForUpdate);

        return eventMapper.toFullDto(updatedEvent);
    }

    private Event getEvent(long eventId) {
        if (eventRepository.findById(eventId) == null) {
            throw new NotFoundException("событие не найдено");
        }
        return eventRepository.findById(eventId);
    }
}