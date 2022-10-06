package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.model.Event;

import java.util.List;

/**
 * Интерфейс для хранения объектов событий
 */
public interface EventRepository extends JpaRepository<Event, Long>, CrudRepository<Event, Long> {
    Event findById(long eventId);

    List<Event> findAllByInitiatorIdInAndStateInAndAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
            List<Long> userId, List<State> states, List<Long> categoryId, String rangeStart, String rangeEnd,
            Pageable pageable);


//    List<Event> findAllByAnnotationContainsIgnoreCaseOrAnnotationContainsIgnoreCaseAndCategoryIdInAndPaidAndEventDateIsAfterAndEventDateIsBeforeAndCoAndConfirmedRequestsIsLessThanParticipantLimit(
//            String text, String textRepeat, List<Long> categoryId, String paid, String rangeStart, String rangeEnd, Pageable pageable);
}