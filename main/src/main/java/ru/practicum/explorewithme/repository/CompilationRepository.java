package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithme.model.Compilation;

import java.util.List;

/**
 * Интерфейс для хранения объектов подборок
 */
public interface CompilationRepository extends JpaRepository<Compilation, Long>, CrudRepository<Compilation, Long> {
    Compilation findById(long compId);

    List<Compilation> findAllByPinned(String pinned, Pageable pageable);
}