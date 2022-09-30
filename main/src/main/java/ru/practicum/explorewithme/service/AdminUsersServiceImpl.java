package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.UserDto;
import ru.practicum.explorewithme.repository.UserRepository;

import java.util.List;

/**
 * Класс, ответственный за операции с пользователями для Администратора
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUsersServiceImpl implements AdminUsersService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsersByUserIdList(List<Long> userId, int from, int size) {
        return null;
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUserByUserId(long userId) {

    }
}
