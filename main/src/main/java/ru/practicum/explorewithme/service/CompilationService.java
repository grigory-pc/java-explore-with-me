package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.model.Compilation;

import java.util.List;

/**
 * Интерфейс для сервисов подборок
 */
public interface CompilationService {
    List<CompilationDto> getAllPinnedCompilations(boolean pinned, int from, int size);

    CompilationDto getCompilationById(long id);

    Compilation getCompilation(long compilationId);
}
