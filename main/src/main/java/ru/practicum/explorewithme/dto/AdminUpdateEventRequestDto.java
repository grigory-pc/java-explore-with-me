package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Dto запроса админа на обновление события
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminUpdateEventRequestDto {
    private String annotation;
    private CategoryDto categoryDto;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private boolean paid;
    private int participantLimit;
    @JsonSerialize(using = StringBooleanSerializer.class)
    private String requestModeration;
    private String title;
    private Location location;
}