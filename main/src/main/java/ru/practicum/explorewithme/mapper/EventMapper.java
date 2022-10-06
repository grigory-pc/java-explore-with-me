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

    Event toEvent(NewEventDto dto);

    EventFullDto toFullDto(Event event);

    EventShortDto toShortDto(Event event);

    AdminUpdateEventRequestDto toDto(Event event);

    List<EventShortDto> toShortDto(Iterable<Event> event);

    List<EventFullDto> toFullDto(Iterable<Event> event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(UpdateEventRequestDto updateEventRequestDto, @MappingTarget Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void adminUpdateEventFromDto(AdminUpdateEventRequestDto adminUpdateEventRequestDto, @MappingTarget Event event);
}
