package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.service.UserCommentService;
import ru.practicum.explorewithme.service.UserEventService;

import java.util.List;

/**
 * Основной контроллер для работы пользователя с событиями
 */
@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
public class UserCommentController {
    private final UserCommentService userCommentService;

    /**
     * Добавление нового комментария пользователем
     *
     * @param userId  пользователя
     * @param eventId события
     * @return возвращает добавленный объект комментария с id
     */
    @PostMapping("/{eventId}")
    public CommentDto addNewCommentByUser(@RequestBody CommentDto commentDto,
                                          @PathVariable long userId,
                                          @PathVariable long eventId) {
        return userCommentService.addComment(userId, eventId, commentDto);
    }

//    /**
//     * Обновляет комментарий, добавленный текущим пользователем
//     *
//     * @param userId  пользователя
//     * @param commentId комментария
//     * @return возвращает обновленный объект комментария
//     */
//    @PatchMapping
//    public CommentDto patchEventByUser(@RequestBody UpdateEventRequestDto updateEventRequestDto,
//                                         @PathVariable long userId) {
//        return userEventService.patchEventByUser(updateEventRequestDto, userId);
//    }
//
//    /**
//     * Возвращает список событий, добавленных текущим пользователем
//     *
//     * @param userId пользователя
//     * @return возвращает список объектов события
//     */
//    @GetMapping
//    public List<EventShortDto> getAllEventsByUserId(@PathVariable long userId,
//                                                    @RequestParam(defaultValue = "0") int from,
//                                                    @RequestParam(defaultValue = "10") int size) {
//        return userEventService.getAllEventsByUserId(userId, from, size);
//    }
//
//
//
//    /**
//     * Отмена события по eventId
//     *
//     * @param userId  пользователя
//     * @param eventId события
//     * @return событие
//     */
//    @PatchMapping("/{eventId}")
//    public EventFullDto cancelEventById(@PathVariable long userId,
//                                        @PathVariable long eventId) {
//        return userEventService.cancelEventByEventIdAndUserId(userId, eventId);
//    }


}