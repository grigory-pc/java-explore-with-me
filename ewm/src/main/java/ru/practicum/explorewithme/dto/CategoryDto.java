package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Dto категории
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryDto {
    private long id;
    private String name;
}
