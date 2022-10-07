package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetBasedPageRequest;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.service.CompilationService;

import java.util.List;

/**
 * Класс, ответственный за операции с подборками
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> getAllPinnedCompilations(String pinned, int from, int size) {
        log.info("Получен запрос на получение списка подборок");

        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        List<Compilation> allCompilations = compilationRepository.findAllByPinned(pinned, pageable);

        return compilationMapper.toDto(allCompilations);
    }

    @Override
    public CompilationDto getCompilationById(long id) {
        log.info("Получен запрос на получение подборки по id" + id);

        return compilationMapper.toDto(getCompilation(id));
    }

    @Override
    public Compilation getCompilation(long compilationId) {
        if (compilationRepository.findById(compilationId) == null) {
            throw new NotFoundException("подборка не найдена");
        }
        return compilationRepository.findById(compilationId);
    }
}