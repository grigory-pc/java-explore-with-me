package ru.practicum.explorewithme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Объект комбинаций подборки и события
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@IdClass(CompilationsEvents.class)
@Table(name = "compilations_events")
public class CompilationsEvents implements Serializable {
    @Id
    @Column(name = "events_id")
    private long eventsId;
    @Id
    @Column(name = "compilation_id")
    private long compilationId;
}
