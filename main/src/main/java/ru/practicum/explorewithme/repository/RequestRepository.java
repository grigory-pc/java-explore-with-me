package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithme.model.Request;

import java.util.List;

/**
 * Интерфейс для хранения объектов запросов
 */
public interface RequestRepository extends JpaRepository<Request, Long>, CrudRepository<Request, Long> {
    List<Request> findAllByRequesterId(long userId);

    List<Request> findAllByEventId(long eventId);

    Request findById(long requestId);
}
