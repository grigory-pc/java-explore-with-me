package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Dto запроса админа на обновление события
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminUpdateEventRequestDto {
    @Size(min = 20, message = "{validation.name.size.too_short}")
    @Size(max = 2000, message = "{validation.name.size.too_long}")
    private String annotation;
    private CategoryDto categoryDto;
    @Size(min = 20, message = "{validation.name.size.too_short}")
    @Size(max = 7000, message = "{validation.name.size.too_long}")
    private String description;
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private boolean paid;
    @Positive
    private int participantLimit;
    private boolean requestModeration;
    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 120, message = "{validation.name.size.too_long}")
    private String title;
    private Location location;
}