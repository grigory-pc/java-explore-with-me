package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.RequestRepository;
import ru.practicum.explorewithme.repository.UserRepository;
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
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Override
    public List<ParticipationRequestDto> getAllRequestsByRequesterId(long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addNewEventRequest(ParticipationRequestDto participationRequestDto, long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelEventRequest(long reqId, long userId) {
        return null;
    }
}
