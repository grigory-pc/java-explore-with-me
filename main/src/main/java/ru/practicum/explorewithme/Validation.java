package ru.practicum.explorewithme;

import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.exception.ValidationException;

@Service
public class Validation {
    public void validationId(long id) {
        if (id <= 0) {
            throw new ValidationException("id должен быть больше 0");
        }
    }
}