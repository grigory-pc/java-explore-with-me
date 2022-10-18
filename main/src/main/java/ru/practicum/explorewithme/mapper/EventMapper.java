package ru.practicum.explorewithme.mapper;

import org.mapstruct.*;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.model.Event;

import java.util.List;

/**
 * Маппер между объектами Event и EventDto
 */
@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "locationLat", source = "location.lat")
    @Mapping(target = "locationLon", source = "location.lon")
    Event toEvent(NewEventDto dto);


    @Mapping(target = "location.lat", source = "locationLat")
    @Mapping(target = "location.lon", source = "locationLon")
    EventFullDto toFullDto(Event event);

    EventShortDto toShortDto(Event event);

    @Mapping(target = "location.lat", source = "locationLat")
    @Mapping(target = "location.lon", source = "locationLon")
    AdminUpdateEventRequestDto toDtoByAdmin(Event event);

    List<EventShortDto> toShortDto(Iterable<Event> event);

    List<EventFullDto> toFullDto(Iterable<Event> event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(UpdateEventRequestDto updateEventRequestDto, @MappingTarget Event event);

    @Mapping(target = "locationLat", source = "location.lat")
    @Mapping(target = "locationLon", source = "location.lon")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void adminUpdateEventFromDto(AdminUpdateEventRequestDto adminUpdateEventRequestDto, @MappingTarget Event event);
}
