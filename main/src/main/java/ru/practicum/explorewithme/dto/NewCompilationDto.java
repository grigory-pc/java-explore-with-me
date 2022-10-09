package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Dto новой подборки событий
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class NewCompilationDto {
    private long id;
    @JsonSerialize(using = StringBooleanSerializer.class)
    private String pinned;
    @NotBlank
    private String title;
    private List<Long> events;
}
