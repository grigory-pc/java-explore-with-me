package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.dto.Status;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.mapper.RequestMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.service.AdminUserService;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.UserRequestService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс, ответственный за операции с запросами для пользователя
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRequestServiceImpl implements UserRequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final RequestMapper requestMapper;
    private final EventService eventService;
    private final AdminUserService adminUserService;

    @Override
    public List<ParticipationRequestDto> getAllRequestsByRequesterId(long userId) {
        log.info("Получен запрос на получение списка запросов");

        adminUserService.getUser(userId);

        List<Request> allRequestsByRequesterId = requestRepository.findAllByRequesterId(userId);

        return requestMapper.toDto(allRequestsByRequesterId);
    }

    @Override
    @Transactional
    public ParticipationRequestDto addNewEventRequest(long userId, long eventId) {
        log.info("Получен запрос на добавление запроса от пользователя" + userId + " на участие в событии: " + eventId);

        User requester = adminUserService.getUser(userId);

        Event event = eventService.getEvent(eventId);

        if (requestRepository.findByEventIdAndRequesterId(eventId, userId) != null) {
            throw new ValidationException("нельзя добавить повторный запрос");
        } else if (event.getInitiator().getId() == userId) {
            throw new ValidationException("нельзя добавить запрос на участие в своём событии");
        } else if (!event.getState().equals(State.PUBLISHED)) {
            throw new ValidationException("нельзя участвовать в неопубликованном событии");
        } else if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new ValidationException("у события достигнут лимит запросов на участие");
        }

        Request request = Request.builder()
                .event(event)
                .requester(requester)
                .created(LocalDateTime.now())
                .build();

        if (event.getParticipantLimit() == 0 || event.getRequestModeration().equals("false")) {
            request.setStatus(Status.CONFIRMED);

            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        } else {
            request.setStatus(Status.PENDING);
        }

        Request newRequest = requestRepository.save(request);

        return requestMapper.toDto(newRequest);
    }

    @Override
    public ParticipationRequestDto cancelEventRequest(long reqId, long userId) {
        log.info("Получен запрос на отмену запроса" + reqId + " от пользователя" + userId);

        adminUserService.getUser(userId);

        Request requestForCancel = getRequest(reqId);
        requestForCancel.setStatus(Status.CANCELED);
        Request updateRequest = requestRepository.save(requestForCancel);

        return requestMapper.toDto(updateRequest);
    }

    @Override
    public Request getRequest(long requestId) {
        if (requestRepository.findById(requestId) == null) {
            throw new NotFoundException("запрос не найден");
        }
        return requestRepository.findById(requestId);
    }
}