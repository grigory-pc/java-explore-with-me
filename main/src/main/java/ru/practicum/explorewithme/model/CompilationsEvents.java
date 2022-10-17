package ru.practicum.explorewithme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

/**
 * Объект комбинаций подборки и события
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@IdClass(CompilationsEventsKey.class)
@Table(name = "compilations_events")
public class CompilationsEvents {
    @Id
    @Column(name = "event_id")
    private long eventId;
    @Id
    @Column(name = "compilation_id")
    private long compilationId;
}
