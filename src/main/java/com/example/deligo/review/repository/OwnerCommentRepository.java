package com.example.deligo.review.repository;

import com.example.deligo.review.entity.OwnerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OwnerCommentRepository extends JpaRepository<OwnerComment, Long> {
    Optional<OwnerComment> findByReviewId(Long reviewId);
    @Query("SELECT oc FROM OwnerComment oc WHERE oc.review.order.store.id = :storeId")
    List<OwnerComment> findByStoreId(@Param("storeId") Long storeId);
}