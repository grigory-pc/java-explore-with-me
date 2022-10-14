package ru.practicum.explorewithmestat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithmestat.model.EndpointHit;

import java.time.LocalDateTime;

/**
 * Интерфейс для хранения объектов статистики
 */
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long>, CrudRepository<EndpointHit, Long> {
    int countByUriAndTimestampIsAfterAndTimestampIsBefore(String uri, LocalDateTime start, LocalDateTime end);

    @Query("select count(e) from EndpointHit e where e.uri = ?1 and e.timestamp > ?2 and e.timestamp < ?3 group by e.ip")
    int countByUniqueIp(String uri, LocalDateTime start, LocalDateTime end);

    EndpointHit findByUri(String uri);

    int countAllByUri(String uri);
}