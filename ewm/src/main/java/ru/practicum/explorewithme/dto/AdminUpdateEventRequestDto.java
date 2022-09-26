package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.dto.CategoryDto;

import java.time.LocalDateTime;

/**
 * Dto запроса админа на обновление события
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminUpdateEventRequestDto {
    private long id;
    private String annotation;
    private CategoryDto categoryDto;
    private String description;
    private LocalDateTime eventDate;
    private String paid;
    private int participantLimit;
    private String requestModeration;
    private String title;
}