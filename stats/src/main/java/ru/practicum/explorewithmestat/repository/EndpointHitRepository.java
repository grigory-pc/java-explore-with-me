package ru.practicum.explorewithmestat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithmestat.model.EndpointHit;

import java.time.LocalDateTime;

/**
 * Интерфейс для хранения объектов статистики
 */
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long>, CrudRepository<EndpointHit, Long> {
    Long countByUriAndTimestampIsAfterAndTimestampIsBefore(LocalDateTime start, LocalDateTime end, String uri);

    EndpointHit findByUri(String uri);
}