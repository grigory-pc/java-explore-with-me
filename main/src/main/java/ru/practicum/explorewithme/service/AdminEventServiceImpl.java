package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetBasedPageRequest;
import ru.practicum.explorewithme.dto.AdminUpdateEventRequestDto;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Event;
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
    private final EventMapper eventMapper;

    @Override
    public List<EventFullDto> getAllEventsByParameters(List<Long> userIdList, List<State> states, List<Long> categoryId,
                                                       String rangeStart, String rangeEnd, int from, int size) {
        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        List<Event> getAllEvents = eventRepository
                .findAllByInitiatorIdInAndStateInAndAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
                        userIdList, states, categoryId,rangeStart,rangeEnd, pageable);


        return eventMapper.toFullDto(getAllEvents);
    }

    @Override
    public AdminUpdateEventRequestDto updateEventByAdmin(AdminUpdateEventRequestDto adminUpdateEventRequestDto,
                                                         long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateStateOfEventByAdmin(long eventId, boolean publish) {
        return null;
    }
}
