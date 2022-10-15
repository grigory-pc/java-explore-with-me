package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.dto.UpdateCommentDto;
import ru.practicum.explorewithme.exception.ValidationException;
import ru.practicum.explorewithme.mapper.CommentMapper;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.CommentRepository;
import ru.practicum.explorewithme.service.AdminUserService;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.UserCommentService;

import java.time.LocalDateTime;

/**
 * Класс, ответственный за операции с комментариями к событию
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCommentServiceImpl implements UserCommentService {
    private final EventService eventService;
    private final AdminUserService adminUserService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto addComment(long userId, long eventId, CommentDto commentDto) {
        log.info("Получен запрос на добавление комментария для пользователя " + userId + " и события" + eventId);

        User user = adminUserService.getUser(userId);
        Event event = eventService.getEvent(eventId);

        if (event.getInitiator().getId() == userId) {
            throw new ValidationException("Комментарий не может оставлять инициатор события");
        } else {
            Comment commentForSave = commentMapper.toComment(commentDto);

            commentForSave.setEvent(event);
            commentForSave.setUser(user);
            commentForSave.setState(State.PENDING);
            commentForSave.setCreated(LocalDateTime.now());

            return commentMapper.toDto(commentRepository.save(commentForSave));
        }
    }

    @Override
    @Transactional
    public CommentDto updateComment(long userId, long commentId, UpdateCommentDto updateCommentDto) {
        log.info("Получен запрос на обновление комментария: " + commentId + ", добавленного" +
                " пользователем: " + userId);

        Comment commentForUpdate = commentRepository.findById(commentId);

        checkUser(commentForUpdate.getUser().getId(), userId);

        commentMapper.updateCommentFromDto(updateCommentDto, commentForUpdate);

        commentForUpdate.setState(State.PENDING);

        Comment commentUpdated = commentRepository.save(commentForUpdate);

        return commentMapper.toDto(commentUpdated);
    }

    @Override
    public void deleteComment(long userId, long commentId) {
        Comment commentForDelete = commentRepository.findById(commentId);

        checkUser(commentForDelete.getUser().getId(), userId);

        commentRepository.deleteById(commentId);
    }

    private void checkUser(long authorId, long userId) {
        if (authorId != userId) {
            throw new ValidationException("Комментарий может изменять или удалять только его автор");
        }
    }
}