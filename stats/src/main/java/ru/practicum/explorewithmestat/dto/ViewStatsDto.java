package ru.practicum.explorewithmestat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Dto статистики
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ViewStatsDto {
    private String app;
    private String uri;
    private int hits;
}
