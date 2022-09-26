package ru.practicum.explorewithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Dto пользователя
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {
    private long id;
    private String name;
    private String email;
}
