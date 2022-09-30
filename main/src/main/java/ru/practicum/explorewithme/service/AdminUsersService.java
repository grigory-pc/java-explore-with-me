package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.UserDto;

import java.util.List;

/**
 * Интерфейс для сервиса пользователей для Администратора
 */
public interface AdminUsersService {
    List<UserDto> getAllUsersByUserIdList(List<Long> userId, int from, int size);

    UserDto addNewUser(UserDto userDto);

    void deleteUserByUserId(long userId);
}
