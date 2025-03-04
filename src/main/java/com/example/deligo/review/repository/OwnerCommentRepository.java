package com.example.deligo.review.repository;

import com.example.deligo.review.entity.OwnerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OwnerCommentRepository extends JpaRepository<OwnerComment, Long> {
    Optional<OwnerComment> findByReviewId(Long reviewId);
    Optional<OwnerComment> findByOwnerId(Long ownerId);
    boolean existsByReviewId(Long reviewId);
    @Query("SELECT oc FROM OwnerComment oc WHERE oc.review.order.store.id = :storeID")
    List<OwnerComment> findByStoreId(@Param("storeID") Long storeID);
    List<OwnerComment> findByReviewIdAndDeletedAtIsNull(Long reviewId);
}