package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.UserDto;
import ru.practicum.explorewithme.model.User;

import java.util.List;

/**
 * Интерфейс для сервиса пользователей для Администратора
 */
public interface AdminUserService {
    List<UserDto> getUsersByIds(List<Long> userId, int from, int size);

    UserDto addNewUser(UserDto userDto);

    void deleteUserById(long userId);

    User getUser(long userId);
}
