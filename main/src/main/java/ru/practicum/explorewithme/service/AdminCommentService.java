package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.model.Comment;

/**
 * Интерфейс для сервиса комментариев для Администратора
 */
public interface AdminCommentService {
    CommentDto publishComment(long commentId, boolean publish);

    Comment getComment(long commentId);
}