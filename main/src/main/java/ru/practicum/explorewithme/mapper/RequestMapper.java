package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explorewithme.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.model.Request;

import java.util.List;

/**
 * Маппер между объектами Request и ParticipationRequestDto
 */
@Mapper(componentModel = "spring")
public interface RequestMapper {
    Request toRequest(ParticipationRequestDto dto);

    ParticipationRequestDto toDto(Request request);

    List<ParticipationRequestDto> toDto(Iterable<Request> request);
}
