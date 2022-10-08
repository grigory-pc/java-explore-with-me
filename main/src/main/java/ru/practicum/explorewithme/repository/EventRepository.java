package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.practicum.explorewithme.dto.State;
import ru.practicum.explorewithme.model.Event;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для хранения объектов событий
 */
public interface EventRepository extends JpaRepository<Event, Long>, CrudRepository<Event, Long> {
    Event findById(long eventId);

    List<Event> findAllByIdIn(List<Long> eventIds);

    Event findByIdAndState(long eventId, State state);

    List<Event> findByInitiatorId(long userId, Pageable pageable);

    List<Event> findByAnnotationContainsIgnoreCaseAndCategoryIdInAndPaidAndEventDateIsAfter(
            String text, List<Long> categoryIds, String paid, LocalDateTime CurrentTime, Pageable pageable);

    List<Event> findByDescriptionContainsIgnoreCaseAndCategoryIdInAndPaidAndEventDateIsAfter(
            String text, List<Long> categoryIds, String paid, LocalDateTime CurrentTime, Pageable pageable);


    List<Event> findAllByAnnotationContainsIgnoreCaseOrDescriptionContainsIgnoreCaseAndCategoryIdInAndPaidAndEventDateIsAfter(
            String text, String repeatText, List<Long> categoryIds, String paid, LocalDateTime CurrentTime, State state,
            Pageable pageable);

    List<Event> findAllByAnnotationContainsIgnoreCaseOrDescriptionContainsIgnoreCaseAndCategoryIdInAndPaidAndEventDateIsAfterAndEventDateIsBefore(
            String text, String repeatText, List<Long> categoryIds, String paid, LocalDateTime rangeStart,
            LocalDateTime rangeEnd, State state, Pageable pageable);


    List<Event> findAllByInitiatorIdInAndStateInAndAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
            List<Long> userId, List<State> states, List<Long> categoryIds, LocalDateTime rangeStart, LocalDateTime rangeEnd,
            Pageable pageable);


//    List<Event> findAllByAnnotationContainsIgnoreCaseOrAnnotationContainsIgnoreCaseAndCategoryIdInAndPaidAndEventDateIsAfterAndEventDateIsBeforeAndCoAndConfirmedRequestsIsLessThanParticipantLimit(
//            String text, String textRepeat, List<Long> categoryId, String paid, String rangeStart, String rangeEnd, Pageable pageable);
}