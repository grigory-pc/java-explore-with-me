package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
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
    private String pinned;
    private String title;
    private List<EventFullDto> events;
}