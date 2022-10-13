package ru.practicum.explorewithme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
    @Column(name = "confirmed_requests")
    private int confirmedRequests;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private boolean paid;
    @Column(name = "participant_limit")
    private int participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state;
    private String title;
    private int views;
    @Column(name = "location_lat")
    private float locationLat;
    @Column(name = "location_lon")
    private float locationLon;
}
