package ru.practicum.explorewithme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.dto.State;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Объект комментариев
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "comments")
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;
    private State state;
    private LocalDateTime created;
}