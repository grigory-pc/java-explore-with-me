package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.dto.Status;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.RequestMapper;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.service.AdminUserService;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.UserRequestService;

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
    public ParticipationRequestDto addNewEventRequest(ParticipationRequestDto participationRequestDto, long userId,
                                                      long eventId) {
        log.info("Получен запрос на добавление запроса от пользователя" + userId + " на участие в событии: " + eventId);

        adminUserService.getUser(userId);

        EventFullDto event = eventService.getEventById(eventId);

        if (event.getParticipantLimit() == 0 || event.getRequestModeration().equals("false")) {
            participationRequestDto.setStatus(Status.CONFIRMED);
        } else {
            participationRequestDto.setStatus(Status.PENDING);
        }

        Request newRequest = requestRepository.save(requestMapper.toRequest(participationRequestDto));

        return requestMapper.toDto(newRequest);
    }

    @Override
    public ParticipationRequestDto cancelEventRequest(long reqId, long userId) {
        log.info("Получен запрос на отмену запроса" + reqId + " от пользователя" + userId);

        adminUserService.getUser(userId);

        Request requestForCancel = getRequest(reqId);
        requestForCancel.setStatus(Status.PENDING);
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