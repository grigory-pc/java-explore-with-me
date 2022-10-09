package ru.practicum.explorewithme.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Dto подборки событий
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CompilationDto {
    private long id;
    @NotBlank
    @JsonSerialize(using = StringBooleanSerializer.class)
    private String pinned;
    @NotBlank
    private String title;
    private List<EventShortDto> events;
}