package ru.practicum.explorewithmestat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithmestat.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для хранения объектов статистики
 */
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long>, CrudRepository<EndpointHit, Long> {

    List<EndpointHit> findAllByTimestampAfterAndTimestampBeforeAndUriIn(LocalDateTime start, LocalDateTime end,
                                                                        List<Long> uris, String unique);

}
