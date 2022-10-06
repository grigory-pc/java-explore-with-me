package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetBasedPageRequest;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.EventService;

import java.util.List;

/**
 * Класс, ответственный за операции с событиями
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventShortDto> getAllEventsByParameters(String text, List<Long> categoryIds, String paid,
                                                        String rangeStart, String rangeEnd, String onlyAvailable,
                                                        String sort, int from, int size) {
        log.info("Получен запрос на получение списка событий");

        Pageable pageable = OffsetBasedPageRequest.of(from, size, Sort.by(Sort.Direction.ASC, sort));

        List<Event> allEvents = eventRepository.findAllByAnnotationContainsIgnoreCaseOrAnnotationContainsIgnoreCaseAndCategoryIdInAndPaidAndEventDateIsAfterAndEventDateIsBeforeAndCoAndConfirmedRequestsIsLessThanParticipantLimit(
                text, text, categoryIds, paid, rangeStart, rangeEnd, pageable);

        return eventMapper.toShortDto(allEvents);
    }

    @Override
    public EventFullDto getEventById(long id) {
        log.info("Получен запрос на получение события по id: " + id);
        Event eventForUpdateViews = getEvent(id);

        int currentCountViews = eventForUpdateViews.getViews();
        eventForUpdateViews.setViews(currentCountViews++);

        return eventMapper.toFullDto(getEvent(id));
    }

    private Event getEvent(long eventId) {
        if (eventRepository.findById(eventId) == null) {
            throw new NotFoundException("событие не найдено");
        }
        return eventRepository.findById(eventId);
    }
}