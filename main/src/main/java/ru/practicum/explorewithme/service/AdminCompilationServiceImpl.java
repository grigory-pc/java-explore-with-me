package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Compilation;
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
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto addNewCompilation(CompilationDto compilationDto) {
        log.info("Получен запрос на добавление подборки:" + compilationDto.getTitle());

        Compilation compilationForSave = compilationMapper.toCompilation(compilationDto);

        return compilationMapper.toDto(compilationRepository.save(compilationForSave));
    }

    @Override
    public void deleteCompilationById(long compilationId) {
        log.info("Получен запрос на удаление подборки id = " + compilationId);

        compilationRepository.deleteById(compilationId);
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
