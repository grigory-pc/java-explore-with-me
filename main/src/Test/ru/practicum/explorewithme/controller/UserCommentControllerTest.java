package ru.practicum.explorewithme.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.explorewithme.dto.CommentDto;
import ru.practicum.explorewithme.dto.StateComment;
import ru.practicum.explorewithme.dto.UpdateCommentDto;
import ru.practicum.explorewithme.service.UserCommentService;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserCommentControllerTest {
    @Mock
    private UserCommentService userCommentService;
    @InjectMocks
    private UserCommentController controller;
    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;
    private String baseUrl;
    private CommentDto newCommentDto;
    private CommentDto commentDto;
    private UpdateCommentDto updateCommentDto;

    @BeforeEach
    void setUp() {
        baseUrl = "/users/{userId}/event/{eventId}/comments";

        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        newCommentDto = CommentDto.builder()
                .id(1L)
                .text("Test comment")
                .authorName("Test user")
                .stateComment(StateComment.PENDING)
                .build();

        commentDto = CommentDto.builder()
                .id(1L)
                .text("Test comment after update")
                .authorName("Test user")
                .stateComment(StateComment.PENDING)
                .build();

        updateCommentDto = UpdateCommentDto.builder()
                .text("Test comment after update")
                .build();
    }

    @DisplayName("GIVEN an comment dto " +
            "WHEN add comment controller method called " +
            "THEN return status OK and returned comment have authorName as in mock")
    @Test
    void Test1_ShouldReturnCommentWhenAdd() throws Exception {
        long userId = 1L;
        long eventId = 1L;

        when(userCommentService.addComment(anyLong(), anyLong(), any(CommentDto.class)))
                .thenReturn(newCommentDto);

        mvc.perform(post(baseUrl, userId, eventId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(newCommentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName", is(newCommentDto.getAuthorName())));
    }

    @DisplayName("GIVEN an comment id and comment dto " +
            "WHEN update comment controller method called " +
            "THEN return status OK and returned comment have text as in mock")
    @Test
    void Test2_shouldReturnCommentWhenUpdate() throws Exception {
        long commentId = 1L;
        long userId = 1L;
        long eventId = 1L;

        when(userCommentService.updateComment(anyLong(), anyLong(), any(UpdateCommentDto.class)))
                .thenReturn(commentDto);

        mvc.perform(patch(baseUrl + "/{commentId}", userId, eventId, commentId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(updateCommentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(updateCommentDto.getText())));
    }

    @DisplayName("GIVEN an comment id  " +
            "WHEN delete by id controller method called " +
            "THEN return status OK and delete service method called 1 time")
    @Test
    void Test3_shouldDeleteComment() throws Exception {
        long commentId = 1L;
        long userId = 1L;
        long eventId = 1L;

        mvc.perform(delete(baseUrl + "/{commentId}", userId, eventId, commentId))
                .andExpect(status().isOk());

        verify(userCommentService, times(1)).deleteComment(anyLong(), anyLong());
    }
}