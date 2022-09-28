package ru.practicum.explorewithme.mapper;

import org.mapstruct.*;
import ru.practicum.explorewithme.dto.EventFullDto;
import ru.practicum.explorewithme.dto.EventShortDto;
import ru.practicum.explorewithme.dto.NewEventDto;
import ru.practicum.explorewithme.dto.UpdateEventRequestDto;
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

    List<EventShortDto> toShortDto(Iterable<Event> event);

    List<EventFullDto> toDto(Iterable<Event> item);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(UpdateEventRequestDto updateEventRequestDto, @MappingTarget Event event);
}
