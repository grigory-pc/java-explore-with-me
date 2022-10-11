package ru.practicum.explorewithmestat.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explorewithmestat.dto.EndpointHitDto;
import ru.practicum.explorewithmestat.model.EndpointHit;

/**
 * Маппер между объектами EndpointHit и EndpointHitDto
 */
@Mapper(componentModel = "spring")
public interface EndpointHitMapper {
    EndpointHit toEndpointHit(EndpointHitDto dto);
}