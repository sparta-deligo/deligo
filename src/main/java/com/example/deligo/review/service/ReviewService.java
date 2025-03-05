package com.example.deligo.review.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.order.entity.Order;
import com.example.deligo.order.repository.OrderRepository;
import com.example.deligo.review.dto.request.ReviewRequest;
import com.example.deligo.review.dto.response.ReviewResponse;
import com.example.deligo.review.entity.Review;
import com.example.deligo.review.repository.OwnerCommentRepository;
import com.example.deligo.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final OwnerCommentRepository ownerCommentRepository;

    @Transactional(readOnly = true)
    public Page<ReviewResponse> getReviewsByStore(Long storeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findByStoreId(storeId, pageable);
        Map<Long, String> ownerComments = getOwnerCommentsByStore(storeId);

        return reviews.map(review ->
                new ReviewResponse(review, ownerComments.getOrDefault(review.getId(), "사장님 댓글이 없습니다."))
        );
    }

    private Map<Long, String> getOwnerCommentsByStore(Long storeId) {
        return ownerCommentRepository.findByStoreId(storeId).stream()
                .collect(Collectors.toMap(
                        comment -> comment.getReview().getId(),
                        comment -> comment.getContent(),
                        (existing, replacement) -> existing
                ));
    }

    @Transactional
    public ReviewResponse createReview(Long userId, ReviewRequest request) {
        Order order = getOrderById(request.getOrderId());

        if (!order.getUser().getId().equals(userId)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }

        if (!order.isDelivered()) {
            throw new CustomException(ExceptionType.REVIEW_CONDITION_NOT_MET);
        }

        reviewRepository.findByOrderId(order.getId()).ifPresent(existing -> {
            throw new CustomException(ExceptionType.REVIEW_ALREADY_EXISTS);
        });

        Review review = Review.builder()
                .user(order.getUser())
                .order(order)
                .rating(request.getRating())
                .content(request.getContent())
                .build();

        reviewRepository.save(review);
        return new ReviewResponse(review, null);
    }

    @Transactional
    public ReviewResponse updateReview(Long userId, Long reviewId, ReviewRequest request) {
        Review review = getReviewById(reviewId);

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }

        review.updateReview(review.getUser(), request.getRating(), request.getContent());
        return new ReviewResponse(review, null);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review review = getReviewById(reviewId);

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }
        review.deleteReview(review.getUser());
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ExceptionType.ORDER_NOT_FOUND));
    }

    private Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionType.REVIEW_NOT_FOUND));
    }
}