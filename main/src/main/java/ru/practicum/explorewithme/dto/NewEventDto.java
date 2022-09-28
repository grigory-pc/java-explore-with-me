package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Dto события
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class NewEventDto {
    private long id;
    @NotBlank
    private String annotation;
    private long categoryId;
    @NotBlank
    private String description;
    @NotNull
    private LocalDateTime eventDate;
    @NotBlank
    private String paid;
    private int participantLimit;
    private String requestModeration;
    @NotBlank
    private String title;
}