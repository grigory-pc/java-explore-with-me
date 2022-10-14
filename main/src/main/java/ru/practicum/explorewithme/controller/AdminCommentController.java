package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.service.AdminCommentService;

/**
 * Основной контроллер для работы Администратора с категориями
 */
@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {
    private final AdminCommentService adminCommentService;

    /**
     * Публикация комментария Администратором
     *
     * @param commentId объекта события
     */
    @PatchMapping("/{commentId}/publish")
    public CommentDto publishCommentByAdmin(@PathVariable long commentId) {
        return adminCommentService.publishComment(commentId, true);
    }

    /**
     * Отклонение комментария Администратором
     *
     * @param commentId объекта события
     */
    @PatchMapping("/{commentId}/reject")
    public CommentDto rejectCommentByAdmin(@PathVariable long commentId) {
        return adminCommentService.publishComment(commentId, false);
    }
}