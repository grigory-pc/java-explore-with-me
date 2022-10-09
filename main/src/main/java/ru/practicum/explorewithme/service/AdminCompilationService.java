package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.NewCompilationDto;

/**
 * Интерфейс для сервиса подборок для Администратора
 */
public interface AdminCompilationService {
    NewCompilationDto addNewCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilationById(long compilationId);

    void deleteEventByIdFromCompilation(long compilationId, long eventId);

    void addEventIdToCompilation(long compilationId, long eventId);

    void updatePinnedOfCompilation(long compilationId, boolean pinned);
}
