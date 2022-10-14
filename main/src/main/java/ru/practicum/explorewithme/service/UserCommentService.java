package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CommentDto;

/**
 * Интерфейс для сервиса комментариев пользователей к событиям
 */
public interface UserCommentService {
    CommentDto addComment(long userId, long eventId, CommentDto commentDto);

    CommentDto updateComment(long userId, long commentId, CommentDto commentDto);

    void deleteComment(long commentId);
}
