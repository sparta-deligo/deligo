package com.example.deligo.review.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.order.entity.Order;
import com.example.deligo.order.repository.OrderRepository;
import com.example.deligo.review.dto.ReviewRequest;
import com.example.deligo.review.dto.ReviewResponse;
import com.example.deligo.review.entity.Review;
import com.example.deligo.review.repository.OwnerCommentRepository;
import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.user.entity.User;
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
    private final ReviewServiceHelper reviewServiceHelper;
    private final OwnerCommentRepository ownerCommentRepository;

    @Transactional(readOnly = true)
    public Page<ReviewResponse> getReviewsByStore(Long storeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findByStoreId(storeId, pageable);
        Map<Long, String> ownerComments = ownerCommentRepository.findByStoreId(storeId).stream()
                .collect(Collectors.toMap(comment -> comment.getReview().getId(), comment -> comment.getContent()));

        return reviews.map(review -> new ReviewResponse(review, ownerComments.get(review.getId())));
    }

    @Transactional
    public ReviewResponse createReview(User user, ReviewRequest request) {
        Order order = getOrderById(request.getOrderId());

        if (!order.isCompleted()) {
            throw new CustomException(ExceptionType.REVIEW_CONDITION_NOT_MET);
        }
        reviewRepository.findByOrderId(order.getId()).ifPresent(existing -> {
            throw new CustomException(ExceptionType.REVIEW_ALREADY_EXISTS);
        });

        Review review = Review.builder()
                .user(user)
                .order(order)
                .rating(request.getRating())
                .content(request.getContent())
                .build();

        reviewRepository.save(review);
        reviewServiceHelper.updateStoreAverageRating(order.getStore().getId());

        return new ReviewResponse(review, null);
    }

    @Transactional
    public ReviewResponse updateReview(User user, Long reviewId, ReviewRequest request) {
        Review review = getReviewById(reviewId);

        if (!review.getUser().equals(user)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }

        review.updateReview(user, request.getRating(), request.getContent());
        reviewServiceHelper.updateStoreAverageRating(review.getOrder().getStore().getId());

        return new ReviewResponse(review, null);
    }

    @Transactional
    public void deleteReview(User user, Long reviewId) {
        Review review = getReviewById(reviewId);

        if (!review.getUser().equals(user)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }

        review.deleteReview(user);
        reviewServiceHelper.updateStoreAverageRating(review.getOrder().getStore().getId());
    }

    @Transactional(readOnly = true)
    public boolean isReviewExists(Long orderId) {
        return reviewRepository.findByOrderId(orderId).isPresent();
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