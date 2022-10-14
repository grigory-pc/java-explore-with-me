package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.UserDto;
import ru.practicum.explorewithme.service.AdminUserService;

import java.util.List;

/**
 * Основной контроллер для работы Администратора с пользователями
 */
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {
    private final AdminUserService adminUserService;

    /**
     * Возвращает список событий, найденных по переданным параметрам поиска
     *
     * @param ids список пользователей
     * @param from с какого id объекта начинать поиск
     * @param size максимальное количество возвращаемых записей
     * @return список объектов пользователей
     */
    @GetMapping
    public List<UserDto> getUsersByIds(@RequestParam List<Long> ids,
                                       @RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "10") int size) {
        return adminUserService.getUsersByIds(ids, from, size);
    }

    /**
     * Добавление нового пользователя Администратором
     *
     * @return возвращает добавленный объект пользователя с id
     */
    @PostMapping
    public UserDto addNewUser(@RequestBody UserDto userDto) {
        return adminUserService.addNewUser(userDto);
    }

    /**
     * Удаление пользователя Администратором
     *
     * @param userId объекта пользователя
     */
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable long userId) {
        adminUserService.deleteUserById(userId);
    }
}