package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.AdminUpdateEventRequestDto;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.repository.EventRepository;

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

    @Override
    public List<EventFullDto> getAllEventsByParameters(List<Long> userId, List<State> states, List<Long> categoryId,
                                                       String rangeStart, String rangeEnd, String sort, int from,
                                                       int size) {
        return null;
    }

    @Override
    public AdminUpdateEventRequestDto updateEventByAdmin(AdminUpdateEventRequestDto adminUpdateEventRequestDto,
                                                         long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateStateOfEventByAdmin(long eventId) {
        return null;
    }
}
