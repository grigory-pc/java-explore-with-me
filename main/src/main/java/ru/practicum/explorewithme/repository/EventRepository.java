package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithme.model.Event;

/**
 * Интерфейс для хранения объектов событий
 */
public interface EventRepository extends JpaRepository<Event, Long>, CrudRepository<Event, Long> {
}
