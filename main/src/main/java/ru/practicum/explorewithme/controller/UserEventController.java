package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.service.UserEventService;

import java.util.List;

/**
 * Основной контроллер для работы пользователя с событиями
 */
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class UserEventController {
    private final UserEventService userEventService;

    /**
     * Возвращает список событий, добавленных текущим пользователем
     *
     * @param userId пользователя
     * @return возвращает список объектов события
     */
    @GetMapping
    public List<EventShortDto> getAllEventsByUserId(@PathVariable long userId,
                                                    @RequestParam(defaultValue = "0") int from,
                                                    @RequestParam(defaultValue = "10") int size) {
        return userEventService.getAllEventsByUserId(userId, from, size);
    }

    /**
     * Обновляет событие, добавленное текущим пользователем
     *
     * @param userId пользователя
     * @return возвращает обновленный объект события
     */
    @PatchMapping
    public EventFullDto patchEventByUser(@RequestBody UpdateEventRequestDto updateEventRequestDto,
                                                  @PathVariable long userId) {
        return userEventService.patchEventByUser(updateEventRequestDto, userId);
    }

    /**
     * Добавление нового события пользователем
     *
     * @param userId пользователя
     * @return возвращает добавленный объект события с id
     */
    @PostMapping
    public NewEventDto addNewEventByUser(@RequestBody NewEventDto newEventDto,
                                         @PathVariable long userId) {
        return userEventService.addNewEventByUser(newEventDto, userId);
    }

    /**
     * Возвращает событие по eventId
     *
     * @param userId  пользователя
     * @param eventId события
     * @return событие
     */
    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable long userId,
                                     @PathVariable long eventId) {
        return userEventService.getEventByEventIdAndUserId(userId, eventId);
    }

    /**
     * Отмена события по eventId
     *
     * @param userId  пользователя
     * @param eventId события
     * @return событие
     */
    @PatchMapping("/{eventId}")
    public EventFullDto cancelEventById(@PathVariable long userId,
                                        @PathVariable long eventId) {
        return userEventService.cancelEventByEventIdAndUserId(userId, eventId);
    }

    /**
     * Возвращает список запросов на участие в событии eventId пользователя userId
     *
     * @param userId  пользователя
     * @param eventId события
     * @return список запросов на участие в событии
     */
    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getAllRequestByUserIdAndEventId(@PathVariable long userId,
                                                                         @PathVariable long eventId) {
        return userEventService.getAllParticipationRequestByUserIdAndEventId(userId, eventId);
    }

    /**
     * Подтверждение чужой заявки на участие в событии текущего пользователя
     *
     * @param userId  пользователя
     * @param eventId события
     * @param reqId   запроса
     * @return запроса на участие в событии
     */
    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequestEventByUser(@PathVariable long userId,
                                                             @PathVariable long eventId,
                                                             @PathVariable long reqId) {
        return userEventService.confirmationParticipationRequest(userId, eventId, reqId, true);
    }

    /**
     * Отклонение чужой заявки на участие в событии текущего пользователя
     *
     * @param userId  пользователя
     * @param eventId события
     * @param reqId   запроса
     * @return запроса на участие в событии
     */
    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequestEventByUser(@PathVariable long userId,
                                                            @PathVariable long eventId,
                                                            @PathVariable long reqId) {
        return userEventService.confirmationParticipationRequest(userId, eventId, reqId, false);
    }
}