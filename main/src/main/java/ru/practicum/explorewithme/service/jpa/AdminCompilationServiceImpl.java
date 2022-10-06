package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.CompilationsEvents;
import ru.practicum.explorewithme.repository.CompilationEventRepository;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.service.AdminCompilationService;

import java.util.List;

/**
 * Класс, ответственный за операции с подборками для Администратора
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationEventRepository compilationEventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public CompilationDto addNewCompilation(CompilationDto compilationDto) {
        log.info("Получен запрос на добавление подборки:" + compilationDto.getTitle());

        Compilation compilationForSave = compilationMapper.toCompilation(compilationDto);

        return compilationMapper.toDto(compilationRepository.save(compilationForSave));
    }

    @Override
    public void deleteCompilationById(long compilationId) {
        log.info("Получен запрос на удаление подборки id = " + compilationId);

        getCompilation(compilationId);

        compilationRepository.deleteById(compilationId);
    }

    @Override
    public void deleteEventByIdFromCompilation(long compilationId, long eventId) {
        log.info("Получен запрос на удаление события id: " + eventId + " в подборке id: " + compilationId);
        getCompilation(compilationId);

        compilationEventRepository.deleteByIdIn(List.of(compilationId, eventId));
    }

    @Override
    @Transactional
    public void addEventIdToCompilation(long compilationId, long eventId) {
        log.info("Получен запрос на добавления события id: " + eventId + " в подборку id: " + compilationId);

//        CompilationsEvents compilationEventsForSave = new CompilationsEvents(compilationId, eventId);
    }

    @Override
    @Transactional
    public void updatePinnedOfCompilation(long compilationId, boolean pinned) {
        Compilation compilationForSave = getCompilation(compilationId);

        if (pinned) {
            compilationForSave.setPinned("true");
        } else {
            compilationForSave.setPinned("false");
        }
    }

    private Compilation getCompilation(long compilationId) {
        if (compilationRepository.findById(compilationId) == null) {
            throw new NotFoundException("подборка не найдена");
        }
        return compilationRepository.findById(compilationId);
    }
}
