package com.example.deligo.review.repository;

import com.example.deligo.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByOrderId(Long orderId);
    List<Review> findAllByOrderStoreIdOrderByCreatedAtDesc(Long storeId);
    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.order.store.id = :storeId AND r.deletedAt IS NULL")
    double findAverageRatingByStoreId(@Param("storeId") Long storeId);
    Page<Review> findByOrder_StoreIdOrderByCreatedAtDesc(Long storeId, Pageable pageable);
}
