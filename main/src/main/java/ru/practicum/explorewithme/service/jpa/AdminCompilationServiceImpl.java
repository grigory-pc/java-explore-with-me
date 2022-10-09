package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Category;
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

    @Override
    @Transactional
    public CompilationDto addNewCompilation(CompilationDto compilationDto) {
        log.info("Получен запрос на добавление подборки:" + compilationDto.getTitle());

        Compilation compilationForSave = compilationMapper.toCompilation(compilationDto);
        Compilation newCompilation = compilationRepository.save(compilationForSave);
        long newCompilationId = newCompilation.getId();

        for (EventShortDto event : compilationDto.getEvents()) {
            CompilationsEvents newCompilationsEvents = CompilationsEvents.builder()
                    .compilationId(newCompilationId)
                    .eventsId(event.getId())
                    .build();

            compilationEventRepository.save(newCompilationsEvents);
        }

        CompilationDto newCompilationDto = compilationMapper.toDto(newCompilation);

        List<Long> allEventsIdOfCompilation = compilationEventRepository.findAllByCompilationId(newCompilationId);
        List<Event> allEventOfCompilation = eventRepository.findAllByIdIn(allEventsIdOfCompilation);

        newCompilationDto.setEvents(eventMapper.toShortDto(allEventOfCompilation));

        return newCompilationDto;
    }

    @Override
    public void deleteCompilationById(long compilationId) {
        log.info("Получен запрос на удаление подборки id = " + compilationId);

        compilationService.getCompilation(compilationId);

        compilationRepository.deleteById(compilationId);
        compilationEventRepository.deleteAllByCompilationId(compilationId);
    }

    @Override
    public void deleteEventByIdFromCompilation(long compilationId, long eventId) {
        log.info("Получен запрос на удаление события id: " + eventId + " в подборке id: " + compilationId);

        compilationService.getCompilation(compilationId);

        compilationEventRepository.deleteByCompilationIdAndAndEventsId(compilationId, eventId);
    }

    @Override
    @Transactional
    public void addEventIdToCompilation(long compilationId, long eventId) {
        log.info("Получен запрос на добавления события id: " + eventId + " в подборку id: " + compilationId);

        compilationService.getCompilation(compilationId);
        eventService.getEvent(eventId);

        CompilationsEvents newCompilationsEvents = CompilationsEvents.builder()
                .compilationId(compilationId)
                .eventsId(eventId)
                .build();

        compilationEventRepository.save(newCompilationsEvents);
    }

    @Override
    @Transactional
    public void updatePinnedOfCompilation(long compilationId, boolean pinned) {
        Compilation compilationForSave = compilationService.getCompilation(compilationId);

        if (pinned) {
            compilationForSave.setPinned("true");
        } else {
            compilationForSave.setPinned("false");
        }
        compilationRepository.save(compilationForSave);
    }
}