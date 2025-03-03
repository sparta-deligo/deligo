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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public Page<ReviewResponse> getReviewsByStore(Long storeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findByOrder_StoreIdOrderByCreatedAtDesc(storeId, pageable)
                .map(ReviewResponse::from);
    }

    @Transactional
    public ReviewResponse createReview(User user, ReviewRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new CustomException(ExceptionType.ORDER_NOT_FOUND));

        if (reviewRepository.findByOrderId(request.getOrderId()).isPresent()) {
            throw new CustomException(ExceptionType.DUPLICATE_RESOURCE, "이미 해당 주문에 대한 리뷰가 존재합니다.");
        }

        Review review = Review.builder()
                .user(user)
                .order(order)
                .store(order.getStore())
                .rating(request.getRating())
                .content(request.getContent())
                .build();

        reviewRepository.save(review);

        updateStoreAverageRating(order.getStore().getId());

        return new ReviewResponse(review);
    }

    @Transactional
    public ReviewResponse updateReview(User user, Long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionType.REVIEW_NOT_FOUND));

        review.updateReview(user, request.getRating(), request.getContent());
        return new ReviewResponse(review);
    }

    @Transactional
    public void deleteReview(User user, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionType.REVIEW_NOT_FOUND));

        review.deleteReview(user);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByStore(Long storeId) {
        return reviewRepository.findAllByOrderStoreIdOrderByCreatedAtDesc(storeId)
                .stream().map(ReviewResponse::new).toList();
    }
}