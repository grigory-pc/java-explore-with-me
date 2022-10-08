package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Dto объекта статистики
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class EndpointHit {
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
