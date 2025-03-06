package com.example.deligo.review.controller;

import com.example.deligo.review.dto.request.ReviewRequest;
import com.example.deligo.review.entity.Review;
import com.example.deligo.review.service.ReviewService;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.entity.UserRole;
import com.example.deligo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewController reviewController;

    private MockMvc mockMvc;
    private User user;
    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();

        user = new User("test@example.com", "password", "Test User", UserRole.OWNER);

        review = new Review(user, null, 5, "Great service!", null);
    }

    @Test
    void testCreateReview_Success() throws Exception {
        ReviewRequest reviewRequest = new ReviewRequest(1L, 5, "Great Service!");
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        mockMvc.perform(post("/reviews/create")
                        .param("userId", "1")
                        .param("orderId", "1")
                        .param("review", reviewRequest.getContent())
                        .param("rating", String.valueOf(reviewRequest.getRating())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Great service!"))
                .andExpect(jsonPath("$.rating").value(5));

        verify(reviewService, times(1)).createReview(eq(1L), any(ReviewRequest.class));
    }
}