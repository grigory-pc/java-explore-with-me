package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.model.CompilationsEvents;

import java.util.Collection;
import java.util.List;

/**
 * Интерфейс для хранения комбинаций подборок и событий
 */
public interface CompilationEventRepository extends JpaRepository<CompilationsEvents, Long>,
        CrudRepository<CompilationsEvents, Long> {

    List<Long> findAllByCompilationId(long compilationId);

    @Modifying
    @Query("DELETE FROM CompilationsEvents ce WHERE CONCAT(ce.eventsId, '~', ce.compilationId) IN :ids")
    void deleteByIdIn(@Param("ids") Collection<Long> ids);
}