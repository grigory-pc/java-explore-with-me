package ru.practicum.explorewithme.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompilationsEventsKey implements Serializable {
    private long eventsId;
    private long compilationId;
}
