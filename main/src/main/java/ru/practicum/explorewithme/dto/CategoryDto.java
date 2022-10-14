package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Dto категории
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryDto {
    private long id;
    @NotBlank
    private String name;
}
