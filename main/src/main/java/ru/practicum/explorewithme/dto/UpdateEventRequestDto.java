package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Dto пользовательского запроса на обновление информации о событии
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateEventRequestDto {
    private long id;
    private String annotation;
    private long category;
    private String description;
    private LocalDateTime eventDate;
    @NotNull
    private long eventId;
    private String paid;
    private int participantLimit;
    private String title;
}