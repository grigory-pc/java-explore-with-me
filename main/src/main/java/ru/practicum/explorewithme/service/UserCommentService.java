package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.dto.UpdateCommentDto;

/**
 * Интерфейс для сервиса комментариев пользователей к событиям
 */
public interface UserCommentService {
    CommentDto addComment(long userId, long eventId, CommentDto commentDto);

    CommentDto updateComment(long userId, long commentId, UpdateCommentDto updateCommentDto);

    void deleteComment(long userId, long commentId);
}
