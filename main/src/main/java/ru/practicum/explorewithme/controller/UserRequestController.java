package ru.practicum.explorewithme.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.service.UserRequestService;

import java.util.List;

/**
 * Основной контроллер для работы пользователя с запросами
 */
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class UserRequestController {
    private final UserRequestService userRequestService;

    /**
     * Возвращает список запросов на участие в чужих событиях данного пользователя userId
     *
     * @param userId пользователя
     * @return список запросов на участие в событиях
     */
    @GetMapping
    public List<ParticipationRequestDto> getAllRequestByUserId(@PathVariable long userId) {
        return userRequestService.getAllRequestsByRequesterId(userId);
    }

    /**
     * Добавление запроса на участие в чужом событии от пользователя userId
     *
     * @param userId пользователя
     * @return запрос на участие в событии
     */
    @PostMapping
    public ParticipationRequestDto addNewRequestByUser(@RequestBody ParticipationRequestDto participationRequestDto,
                                                       @PathVariable long userId,
                                                       @PathVariable long eventId) {
        return userRequestService.addNewEventRequest(participationRequestDto, userId, eventId);
    }

    /**
     * Отмена запроса пользователя userId на участие в чужом событии
     *
     * @param userId    пользователя
     * @param requestId пользователя
     * @return запрос на участие в событии
     */
    @PostMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestByUser(@PathVariable long userId,
                                                       @PathVariable long requestId) {
        return userRequestService.cancelEventRequest(requestId, userId);
    }
}