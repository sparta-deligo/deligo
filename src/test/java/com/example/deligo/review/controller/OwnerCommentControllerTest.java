package com.example.deligo.review.controller;

import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.review.dto.request.OwnerCommentRequest;
import com.example.deligo.review.service.OwnerCommentService;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OwnerCommentControllerTest {

    @Mock
    private OwnerCommentService ownerCommentService;

    @InjectMocks
    private OwnerCommentController ownerCommentController;

    private MockMvc mockMvc;
    private User owner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ownerCommentController).build();
        owner = new User("test@example.com", "password", "Test User", UserRole.OWNER);
    }

    @Test
    void testCreateOwnerComment_Success() throws Exception {
        OwnerCommentRequest request = new OwnerCommentRequest("Great service!");
        mockMvc.perform(post("/reviews/1/comments")
                        .header("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Great service!\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/reviews/1/comments"));

        verify(ownerCommentService, times(1)).createOwnerComment(eq(1L), eq(1L), eq("Great service!"));
    }

    @Test
    void testCreateOwnerComment_Fail_InvalidContent() throws Exception {
        OwnerCommentRequest request = new OwnerCommentRequest("");

        mockMvc.perform(post("/reviews/1/comments")
                        .header("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("댓글 내용은 필수입니다."));

        verify(ownerCommentService, times(0)).createOwnerComment(anyLong(), anyLong(), anyString());
    }

    @Test
    void testUpdateOwnerComment_Success() throws Exception {
        OwnerCommentRequest request = new OwnerCommentRequest("Updated comment");
        mockMvc.perform(put("/reviews/1/comments/1")
                        .header("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Updated comment\"}"))
                .andExpect(status().isNoContent());

        verify(ownerCommentService, times(1)).updateOwnerComment(eq(1L), eq(1L), eq("Updated comment"));
    }

    @Test
    void testDeleteOwnerComment_Success() throws Exception {
        mockMvc.perform(delete("/reviews/1/comments/1")
                        .header("userId", "1"))
                .andExpect(status().isNoContent());

        verify(ownerCommentService, times(1)).deleteOwnerComment(eq(1L), eq(1L));
    }

    @Test
    void testCreateOwnerComment_Fail_NotOwner() throws Exception {
        User nonOwner = new User("nonowner@example.com", "password", "Non Owner", UserRole.OWNER);

        mockMvc.perform(post("/reviews/1/comments")
                        .header("userId", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Great service!\"}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(ExceptionType.NO_PERMISSION_ACTION.getMessage()));

        verify(ownerCommentService, times(0)).createOwnerComment(anyLong(), anyLong(), anyString());
    }
}