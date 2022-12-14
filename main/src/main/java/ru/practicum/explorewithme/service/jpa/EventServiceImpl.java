package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetBasedPageRequest;
import ru.practicum.explorewithme.client.StatsClient;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.EventService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    private final StatsClient statsClient;

    /**
     * Возвращает список событий, найденных по параметрам запроса
     */
    @Override
    public List<EventShortDto> getAllEventsByParameters(String text, List<Long> categoryIds, boolean paid,
                                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                        boolean onlyAvailable, SortVariant sort, int from, int size) {
        log.info("Получен запрос на получение списка событий");

        List<Event> allEvents;
        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        if (sort.equals(SortVariant.EVENT_DATE)) {
            pageable = OffsetBasedPageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "eventDate"));
        }

        if (rangeStart == null || rangeEnd == null) {
            allEvents = eventRepository.findAllByTextAndParametersWithoutTime(text, text, categoryIds, paid,
                    LocalDateTime.now(), State.PUBLISHED, pageable);
        } else {
            allEvents = eventRepository.findAllByTextAndParameters(text, text, categoryIds, paid, rangeStart, rangeEnd,
                    State.PUBLISHED, pageable);
        }

        if (onlyAvailable) {
            allEvents.stream()
                    .filter(event -> event.getParticipantLimit() > event.getConfirmedRequests())
                    .collect(Collectors.toList());
        }

        List<EventShortDto> eventShortDtos = eventMapper.toShortDto(allEvents);

        for (EventShortDto eventShortDto : eventShortDtos) {
            int views = getEventViews(eventShortDto.getId());
            eventShortDto.setViews(views);
        }

        if (sort.equals(SortVariant.VIEWS)) {
            eventShortDtos.stream()
                    .sorted(Comparator.comparingInt(EventShortDto::getViews))
                    .collect(Collectors.toList());
        }

        return eventShortDtos;
    }

    /**
     * Возвращает событие, найденное по id
     */
    @Override
    public EventFullDto getEventById(long id) {
        log.info("Получен запрос на получение события по id: " + id);
        getEvent(id);

        Event event = eventRepository.findByIdAndState(id, State.PUBLISHED);

        EventFullDto eventFullDto = eventMapper.toFullDto(event);
        int views = getEventViews(id);
        eventFullDto.setViews(views);

        return eventFullDto;
    }

    @Override
    public Event getEvent(long eventId) {
        if (eventRepository.findById(eventId) == null) {
            throw new NotFoundException("событие не найдено");
        }
        return eventRepository.findById(eventId);
    }

    @Override
    public int getEventViews(long eventId) {
        String uri = "/events/" + eventId;

        return statsClient.getStatsByUri(uri);
    }
}