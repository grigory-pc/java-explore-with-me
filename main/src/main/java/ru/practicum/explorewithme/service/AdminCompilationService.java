package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CompilationDto;

/**
 * Интерфейс для сервиса подборок для Администратора
 */
public interface AdminCompilationService {
    CompilationDto addNewCompilation(CompilationDto compilationDto);

    void deleteCompilationById(long compilationId);

    void deleteEventByIdFromCompilation(long compilationId, long eventId);

    void addEventIdToCompilation(long compilationId, long eventId);

    void updatePinnedOfCompilation(long compilationId, boolean pinned);
}
