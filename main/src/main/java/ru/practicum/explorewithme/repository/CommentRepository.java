package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithme.model.Comment;

import java.util.List;

/**
 * Интерфейс для хранения объектов комментариев
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, CrudRepository<Comment, Long> {
    Comment findById(long commentId);

    List<Comment> findAllByEventId(long eventId);
}