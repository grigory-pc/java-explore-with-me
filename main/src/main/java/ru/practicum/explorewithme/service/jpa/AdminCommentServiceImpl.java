package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.CommentMapper;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.repository.CommentRepository;
import ru.practicum.explorewithme.service.AdminCommentService;

/**
 * Класс, ответственный за операции с комментариями к событию для Администратора
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto publishComment(long commentId, boolean publish) {
        log.info("Получен запрос от администратора на обновление статуса публикации для комментария: " + commentId);

        Comment commentForPublish = getComment(commentId);

        if (publish) {
            commentForPublish.setState(State.PUBLISHED);
        } else {
            commentForPublish.setState(State.CANCELED);
        }

        Comment updatedComment = commentRepository.save(commentForPublish);

        return commentMapper.toDto(updatedComment);
    }

    @Override
    public Comment getComment(long commentId) {
        if (commentRepository.findById(commentId) == null) {
            throw new NotFoundException("комментарий не найден");
        }
        return commentRepository.findById(commentId);
    }


}