package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewCompilationDto;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.CompilationsEvents;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.repository.CompilationEventRepository;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.AdminCompilationService;
import ru.practicum.explorewithme.service.CompilationService;
import ru.practicum.explorewithme.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

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
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;
    private final EventMapper eventMapper;
    private final CompilationService compilationService;
    private final EventService eventService;

    /**
     * Добавляет новую подборку событий
     */
    @Override
    @Transactional
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {
        log.info("Получен запрос на добавление подборки:" + newCompilationDto.getTitle());

        Compilation compilationForSave = compilationMapper.toCompilation(newCompilationDto);
        Compilation newCompilation = compilationRepository.save(compilationForSave);
        long newCompilationId = newCompilation.getId();

        for (Long eventId : newCompilationDto.getEvents()) {
            CompilationsEvents newCompilationsEvents = CompilationsEvents.builder()
                    .compilationId(newCompilationId)
                    .eventId(eventId)
                    .build();

            compilationEventRepository.save(newCompilationsEvents);
        }

        CompilationDto addedCompilationDto = compilationMapper.toDto(newCompilation);

        List<CompilationsEvents> allEventsOfCompilation =
                compilationEventRepository.findAllByCompilationId(newCompilationId);

        List<Long> eventsIds = allEventsOfCompilation.stream()
                .map(event -> event.getEventId())
                .collect(Collectors.toList());

        List<Event> allEvents = eventRepository.findAllByIdIn(eventsIds);

        List<EventShortDto> allEventShortDto = eventMapper.toShortDto(allEvents);

        addedCompilationDto.setEvents(allEventShortDto);

        return addedCompilationDto;
    }

    /**
     * Удаляет подборку событий
     */
    @Override
    public void deleteCompilationById(long compilationId) {
        log.info("Получен запрос на удаление подборки id = " + compilationId);

        compilationService.getCompilation(compilationId);

        compilationRepository.deleteById(compilationId);
        compilationEventRepository.deleteAllByCompilationId(compilationId);
    }

    /**
     * Удаляет событие из подборки
     */
    @Override
    public void deleteEventByIdFromCompilation(long compilationId, long eventId) {
        log.info("Получен запрос на удаление события id: " + eventId + " в подборке id: " + compilationId);

        compilationService.getCompilation(compilationId);

        compilationEventRepository.deleteByCompilationIdAndAndEventId(compilationId, eventId);
    }

    /**
     * Добавляет событие в подборку
     */
    @Override
    @Transactional
    public void addEventIdToCompilation(long compilationId, long eventId) {
        log.info("Получен запрос на добавления события id: " + eventId + " в подборку id: " + compilationId);

        compilationService.getCompilation(compilationId);
        eventService.getEvent(eventId);

        CompilationsEvents newCompilationsEvents = CompilationsEvents.builder()
                .compilationId(compilationId)
                .eventId(eventId)
                .build();

        compilationEventRepository.save(newCompilationsEvents);
    }

    /**
     * Прикрепляет или открепляет подборку событий
     */
    @Override
    @Transactional
    public void updatePinnedOfCompilation(long compilationId, boolean pinned) {
        Compilation compilationForSave = compilationService.getCompilation(compilationId);

        if (pinned) {
            compilationForSave.setPinned(true);
        } else {
            compilationForSave.setPinned(false);
        }
        compilationRepository.save(compilationForSave);
    }
}