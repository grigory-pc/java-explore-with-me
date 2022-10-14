package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Класс для координат
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Location {
    private float lat;
    private float lon;
}
