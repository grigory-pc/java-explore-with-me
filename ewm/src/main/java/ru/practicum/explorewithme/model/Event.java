package ru.practicum.explorewithme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.admin.users.model.User;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.dto.State;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Объект событий
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private String paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private String requestModeration;
    private State state;
    private String title;
    private int views;
}
