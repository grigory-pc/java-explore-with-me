package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.User;

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
public class EventShortDto {
    private long id;
    private String annotation;
    private Category category;
    private int confirmedRequests;
    @NotNull
    private LocalDateTime eventDate;
    private User initiator;
    @NotBlank
    private String paid;
    @NotBlank
    private String title;
    private int views;
}
