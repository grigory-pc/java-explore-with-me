package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    private long categoryId;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull
    private long eventId;
    private String paid;
    private int participantLimit;
    private String title;
}