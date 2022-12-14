package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetBasedPageRequest;
import ru.practicum.explorewithme.dto.UserDto;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.UserMapper;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.UserRepository;
import ru.practicum.explorewithme.service.AdminUserService;

import java.util.List;

/**
 * Класс, ответственный за операции с пользователями для Администратора
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Возвращает список пользователей по списку их id
     */
    @Override
    public List<UserDto> getUsersByIds(List<Long> userIds, int from, int size) {
        log.info("Получен запрос на получение списка пользователей");

        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        List<User> users = userRepository.findByIdIn(userIds, pageable);

        return userMapper.toDto(users);
    }

    /**
     * Добавляет нового пользователя
     */
    @Override
    @Transactional
    public UserDto addNewUser(UserDto userDto) {
        log.info("Получен запрос на добавление пользователя " + userDto.getEmail());

        User newUser = userRepository.save(userMapper.toUser(userDto));

        return userMapper.toDto(newUser);
    }

    /**
     * Удаляет пользователя
     */
    @Override
    public void deleteUserById(long userId) {
        log.info("Получен запрос на удаление пользователя c id = " + userId);

        getUser(userId);

        userRepository.deleteById(userId);
    }

    @Override
    public User getUser(long userId) {
        if (userRepository.findById(userId) == null) {
            throw new NotFoundException("пользователь не найден");
        }
        return userRepository.findById(userId);
    }
}