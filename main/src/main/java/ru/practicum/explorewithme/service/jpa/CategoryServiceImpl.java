package ru.practicum.explorewithme.service.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetBasedPageRequest;
import ru.practicum.explorewithme.dto.CategoryDto;
import ru.practicum.explorewithme.exception.NotFoundException;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.repository.CategoryRepository;
import ru.practicum.explorewithme.service.CategoryService;

import java.util.List;

/**
 * Класс, ответственный за операции с категориями
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        Pageable pageable = OffsetBasedPageRequest.of(from, size);

        List<Category> allCategories = categoryRepository.findAllBy(pageable);

        return categoryMapper.toDto(allCategories);
    }

    @Override
    public CategoryDto getCategoryById(long id) {
        return categoryMapper.toDto(getCategory(id));
    }

    private Category getCategory(long categoryId) {
        if (categoryRepository.findById(categoryId) == null) {
            throw new NotFoundException("категория не найдена");
        }
        return categoryRepository.findById(categoryId);
    }

}
