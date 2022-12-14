package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithme.model.Category;

import java.util.List;

/**
 * Интерфейс для хранения объектов категорий
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, CrudRepository<Category, Long> {
    Category findById(long id);

    List<Category> findAllBy(Pageable pageable);
}
