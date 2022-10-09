package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.awt.*;
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
    @NotNull
    private Category category;
    private int confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    private String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull
    private User initiator;
    @NotBlank
    private String paid;
    @Builder.Default
    private int participantLimit = 0;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private String requestModeration;
    private State state;
    @NotBlank
    private String title;
    private int views;
//    private String location;
    private Point location;
}
