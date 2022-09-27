package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.model.Compilation;

import java.util.List;

/**
 * Маппер между объектами Compilation и CompilationDto
 */
@Mapper(componentModel = "spring")
public interface CompilationMapper {
    Compilation toCompilation(CompilationDto dto);

    CompilationDto toDto(Compilation compilation);

    List<CompilationDto> toDto(Iterable<Compilation> compilation);
}
