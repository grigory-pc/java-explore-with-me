package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.repository.CompilationRepository;

/**
 * Класс, ответственный за операции с подборками для Администратора
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;

    @Override
    public CompilationDto addNewCompilation(CompilationDto compilationDto) {
        return null;
    }

    @Override
    public void deleteCompilationById(long compilationId) {

    }

    @Override
    public void deleteEventByIdFromCompilation(long compilationId, long eventId) {

    }

    @Override
    public void addEventIdToCompilation(long compilationId, long eventId) {

    }

    @Override
    public void updatePinnedOfCompilation(long compilationId, boolean pinned) {

    }
}
