package ru.practicum.explorewithme.mapper;

import org.mapstruct.Mapper;
import ru.practicum.explorewithme.dto.UserDto;
import ru.practicum.explorewithme.dto.UserShortDto;
import ru.practicum.explorewithme.model.User;

import java.util.List;

/**
 * Маппер между объектами User и UserDto
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto dto);

    UserDto toDto(User user);

    UserShortDto toShortDto(User user);

    List<UserDto> toDto(Iterable<User> users);

    List<UserShortDto> toShortDto(Iterable<User> users);
}