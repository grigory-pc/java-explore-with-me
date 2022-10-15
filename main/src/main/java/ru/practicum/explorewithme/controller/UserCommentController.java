package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.service.UserCommentService;

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
    @PostMapping("/event/{eventId}")
    public CommentDto addNewCommentByUser(@RequestBody CommentDto commentDto,
                                          @PathVariable long userId,
                                          @PathVariable long eventId) {
        return userCommentService.addComment(userId, eventId, commentDto);
    }

    /**
     * Обновляет комментарий, добавленный текущим пользователем
     *
     * @param userId    пользователя
     * @param commentId комментария
     * @return возвращает обновленный объект комментария
     */
    @PatchMapping("/{commentId}")
    public CommentDto updateCommentByUser(@RequestBody UpdateCommentDto updateCommentDto,
                                          @PathVariable long userId,
                                          @PathVariable long commentId) {
        return userCommentService.updateComment(userId, commentId, updateCommentDto);
    }

    /**
     * Удаление комментария по commentId
     *
     * @param userId    пользователя
     * @param commentId комментария
     * @return событие
     */
    @DeleteMapping("/{commentId}")
    public void cancelEventById(@PathVariable long userId,
                                @PathVariable long commentId) {
        userCommentService.deleteComment(userId, commentId);
    }


}