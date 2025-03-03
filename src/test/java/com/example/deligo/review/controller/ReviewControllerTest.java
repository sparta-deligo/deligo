package com.example.deligo.review.controller;

import com.example.deligo.order.entity.Order;
import com.example.deligo.review.entity.Review;
import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    private User user;
    private Order order;
    private Review review;

    @BeforeEach
    void setUp() {
        user = new User(1L, "test@example.com", "password", "tester", "ROLE_USER");
        order = new Order(1L, user, "배송지", "가게 코멘트", "라이더 코멘트", "COMPLETED");

        review = Review.builder()
                .user(user)
                .order(order)
                .rating(5)
                .content("좋아요!")
                .build();

        reviewRepository.save(review);
    }

    @Test
    void 리뷰_조회_성공() throws Exception {
        mockMvc.perform(get("/api/reviews/" + review.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(review.getId()))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.content").value("좋아요!"));
    }

    @Test
    void 리뷰_삭제_성공() throws Exception {
        mockMvc.perform(delete("/api/reviews/" + review.getId()))
                .andExpect(status().isNoContent());

        Optional<Review> deletedReview = reviewRepository.findById(review.getId());
        assertThat(deletedReview).isEmpty();
    }
}