package com.example.deligo.review.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.order.entity.Order;
import com.example.deligo.order.repository.OrderRepository;
import com.example.deligo.review.dto.ReviewRequest;
import com.example.deligo.review.dto.ReviewResponse;
import com.example.deligo.review.entity.Review;
import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ReviewService reviewService;

    private User user;
    private Order order;
    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(1L, "test@example.com", "password", "tester", "ROLE_USER");
        order = mock(Order.class); // Mock 객체로 변경

        when(order.getId()).thenReturn(1L);
        when(order.isCompleted()).thenReturn(true);
        when(order.getReview()).thenReturn(null);

        review = Review.builder()
                .user(user)
                .order(order)
                .rating(5)
                .content("맛있어요!")
                .build();
    }

    @Test
    @DisplayName("리뷰 생성 성공")
    void createReview_Success() {
        // Given
        ReviewRequest request = new ReviewRequest(order.getId(), 5, "맛있어요!");
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(reviewRepository.findByOrderId(order.getId())).thenReturn(Optional.empty());
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // When
        ReviewResponse response = reviewService.createReview(user, request);

        // Then
        assertNotNull(response);
        assertEquals(5, response.getRating());
        assertEquals("맛있어요!", response.getContent());

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("중복 리뷰 생성 시 예외 발생")
    void createReview_Fail_DuplicateReview() {
        // Given
        ReviewRequest request = new ReviewRequest(order.getId(), 5, "맛있어요!");
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(reviewRepository.findByOrderId(order.getId())).thenReturn(Optional.of(review));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            reviewService.createReview(user, request);
        });

        assertEquals(ExceptionType.DUPLICATE_RESOURCE, exception.getExceptionType());
    }

    @Test
    @DisplayName("리뷰 삭제 성공")
    void deleteReview_Success() {
        // Given
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

        // When & Then
        assertDoesNotThrow(() -> reviewService.deleteReview(user, review.getId()));
        verify(reviewRepository, times(1)).delete(any(Review.class));
    }
}