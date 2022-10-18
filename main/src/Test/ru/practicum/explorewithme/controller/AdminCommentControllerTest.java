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
import ru.practicum.explorewithme.service.AdminCommentService;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminCommentControllerTest {

    @Mock
    private AdminCommentService adminCommentService;

    @InjectMocks
    private AdminCommentController controller;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();
    private MockMvc mvc;
    private String baseUrl;
    private CommentDto commentDto;


    @BeforeEach
    void setUp() {
        baseUrl = "/admin/comments";

        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        commentDto = CommentDto.builder()
                .id(1L)
                .text("Test comment")
                .authorName("Test user")
                .created(LocalDateTime.now())
                .stateComment(StateComment.PUBLISHED)
                .build();
    }

    @DisplayName("GIVEN a comment id " +
            "WHEN call endpoint for publish of comment " +
            "THEN return OK and returned updated comment have stateComment is Publish")
    @Test
    void Test1_shouldReturnUpdatedComment() throws Exception {
        long commentId = 1L;

        when(adminCommentService.publishComment(anyLong(), anyBoolean()))
                .thenReturn(commentDto);

        mvc.perform(patch(baseUrl + "/{commentId}/publish", commentId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stateComment", is(String.valueOf(commentDto.getStateComment()))));
    }
}