package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
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
    @Size(min = 20, message = "{validation.name.size.too_short}")
    @Size(max = 2000, message = "{validation.name.size.too_long}")
    private String annotation;
    @NotNull
    @JsonProperty("category")
    private long categoryId;
    @NotBlank
    @Size(min = 20, message = "{validation.name.size.too_short}")
    @Size(max = 7000, message = "{validation.name.size.too_long}")
    private String description;
    @Future
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotBlank
    @Builder.Default
    private boolean paid = false;
    @Positive
    private int participantLimit;
    @Builder.Default
    private boolean requestModeration = true;
    @NotBlank
    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 120, message = "{validation.name.size.too_long}")
    private String title;
    private Location location;
}