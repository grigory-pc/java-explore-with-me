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
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.CompilationsEvents;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.CompilationEventRepository;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.CompilationService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, ответственный за операции с подборками
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationEventRepository compilationEventRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;
    private final EventMapper eventMapper;

    @Override
    public List<CompilationDto> getAllPinnedCompilations(String pinned, int from, int size) {
        log.info("Получен запрос на получение списка подборок");

        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        List<CompilationDto> allCompilationsDto = compilationMapper.toDto(compilationRepository.findAllByPinned(pinned,
                pageable));

        for (CompilationDto compilationDto : allCompilationsDto) {
            long compId = compilationDto.getId();

            List<CompilationsEvents> allEventsOfCompilation =
                    compilationEventRepository.findAllByCompilationId(compId);

            List<Long> eventsIds = allEventsOfCompilation.stream()
                    .map(event -> event.getEventsId())
                    .collect(Collectors.toList());

            List<Event> allEventOfCompilation = eventRepository.findAllByIdIn(eventsIds);

            compilationDto.setEvents(eventMapper.toShortDto(allEventOfCompilation));
        }

        return allCompilationsDto;
    }

    @Override
    public CompilationDto getCompilationById(long compId) {
        log.info("Получен запрос на получение подборки по id" + compId);

        CompilationDto compilationDto = compilationMapper.toDto(getCompilation(compId));

        List<CompilationsEvents> allEventsOfCompilation =
                compilationEventRepository.findAllByCompilationId(compId);

        List<Long> eventsIds = allEventsOfCompilation.stream()
                .map(event -> event.getEventsId())
                .collect(Collectors.toList());

        List<Event> allEventOfCompilation = eventRepository.findAllByIdIn(eventsIds);

        compilationDto.setEvents(eventMapper.toShortDto(allEventOfCompilation));

        return compilationDto;
    }

    @Override
    public Compilation getCompilation(long compilationId) {
        if (compilationRepository.findById(compilationId) == null) {
            throw new NotFoundException("подборка не найдена");
        }
        return compilationRepository.findById(compilationId);
    }
}