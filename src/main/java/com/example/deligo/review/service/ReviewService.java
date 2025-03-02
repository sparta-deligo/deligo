package com.example.deligo.review.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.order.entity.Order;
import com.example.deligo.review.dto.ReviewRequest;
import com.example.deligo.review.entity.Review;
import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.order.repository.OrderRepository;
import com.example.deligo.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    // ✅ 리뷰 생성
    @Transactional
    public Review createReview(Long orderId, ReviewRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ExceptionType.NOT_FOUND));

        if (!order.getStatus().equals("COMPLETED")) {
            throw new CustomException(ExceptionType.REVIEW_CONDITION_NOT_MET);
        }

        if (reviewRepository.findById(orderId).isPresent()) {
            throw new CustomException(ExceptionType.DUPLICATE_RESOURCE);
        }

        Review review = Review.builder()
                .user(order.getUser())
                .order(order)
                .rating(request.getRating())
                .content(request.getContent())
                .build();

        return reviewRepository.save(review);
    }

    // ✅ 리뷰 수정
    @Transactional
    public Review updateReview(Long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionType.NOT_FOUND));

        review.updateReview(request.getRating(), request.getContent());

        return review;
    }

    // ✅ 가게 리뷰 조회 (별점 필터링 가능)
    @Transactional(readOnly = true)
    public List<Review> getReviewsByStore(Long storeId, int minRating, int maxRating) {
        Store store = new Store(); // Store 엔티티는 가게 조회 로직에 맞게 가져오면 됨.
        store.setId(storeId);

        return reviewRepository.findByOrder_StoreAndRatingBetweenOrderByCreatedAtDesc(store, minRating, maxRating);
    }
}