package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithme.model.User;

import java.util.List;

/**
 * Интерфейс для хранения объектов пользователей
 */
public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
    User findById(long userId);

    List<User> findByIdIn(List<Long> userIds, Pageable pageable);
}
