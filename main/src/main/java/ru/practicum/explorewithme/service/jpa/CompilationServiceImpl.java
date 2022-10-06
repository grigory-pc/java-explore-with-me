package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CompilationDto;
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

    @Override
    public List<CompilationDto> getAllPinnedCompilations(String pinned, int from, int size) {
        return null;
    }

    @Override
    public CompilationDto getCompilationById(long id) {
        return null;
    }
}
