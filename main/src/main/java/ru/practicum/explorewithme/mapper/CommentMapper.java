package ru.practicum.explorewithme.mapper;

import org.mapstruct.*;
import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.dto.UpdateCommentDto;
import ru.practicum.explorewithme.model.Comment;

import java.util.List;

/**
 * Маппер между объектами Comment и CommentDto
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CommentDto dto);

    @Mapping(target = "authorName", source = "user.name")
    CommentDto toDto(Comment comment);

    List<CommentDto> toDto(Iterable<Comment> comments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommentFromDto(UpdateCommentDto updateCommentDto, @MappingTarget Comment comment);
}