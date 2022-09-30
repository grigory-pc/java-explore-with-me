package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CompilationDto;

import java.util.List;

/**
 * Интерфейс для сервисов подборок
 */
public interface CompilationService {
    List<CompilationDto> getAllPinnedCompilations(String pinned, int from, int size);

    CompilationDto getCompilationById(long id);
}
