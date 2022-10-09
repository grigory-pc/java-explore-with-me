package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
public class NewEventDto {
    private long id;
    @NotBlank
    private String annotation;
    @NotNull
    private long categoryId;
    @NotBlank
    private String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotBlank
    private String paid;
    private int participantLimit;
    @Builder.Default
    private String requestModeration = "true";
    @NotBlank
    private String title;
//    private String location;
//    private Point location;
}