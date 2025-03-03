package com.example.deligo.review.repository;

import com.example.deligo.review.entity.OwnerComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerCommentRepository extends JpaRepository<OwnerComment, Long> {
    Optional<OwnerComment> findByReviewId(Long reviewId);
    Optional<OwnerComment> findByOwnerId(Long ownerId);
    boolean existsByReviewId(Long reviewId);
}
