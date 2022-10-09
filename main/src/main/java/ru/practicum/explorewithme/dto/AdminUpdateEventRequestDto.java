package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.awt.*;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private String paid;
    private int participantLimit;
    private String requestModeration;
    private String title;
//    private String location;
    private Point location;
}