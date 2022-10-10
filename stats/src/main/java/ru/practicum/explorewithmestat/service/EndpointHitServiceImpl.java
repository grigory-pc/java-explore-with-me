package ru.practicum.explorewithmestat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmestat.dto.EndpointHitDto;
import ru.practicum.explorewithmestat.dto.ViewStatsDto;
import ru.practicum.explorewithmestat.mapper.EndpointHitMapper;
import ru.practicum.explorewithmestat.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс, ответственный за операции со статистикой
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EndpointHitServiceImpl implements EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;
    private final EndpointHitMapper endpointHitMapper;

    @Override
    public void addStats(EndpointHitDto endpointHitDto) {
        log.info("Получен запрос на добавление статистики для uri: " + endpointHitDto.getUri());

        endpointHitRepository.save(endpointHitMapper.toEndpointHit(endpointHitDto));
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<Long> uris, String unique) {
        log.info("Получен запрос на получение статистики");


        return null;
    }
}
