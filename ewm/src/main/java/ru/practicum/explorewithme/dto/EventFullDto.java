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
public class EventFullDto {
    private long id;
    private String annotation;
    private CategoryDto categoryDto;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    @NotNull
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    @NotBlank
    private String paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private String requestModeration;
    private State state;
    @NotBlank
    private String title;
    private int views;
}
