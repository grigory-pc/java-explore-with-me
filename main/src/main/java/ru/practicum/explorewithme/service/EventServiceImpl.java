package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.repository.EventRepository;

import java.time.LocalDateTime;
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

    @Override
    public List<EventShortDto> searchEventByTextAndParameters(String text, List<Long> categoryId, String paid,
                                                              LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                              String onlyAvailable, String sort, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto getEventById(long id) {
        return null;
    }
}
