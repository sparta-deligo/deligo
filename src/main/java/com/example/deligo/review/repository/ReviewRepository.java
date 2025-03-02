package com.example.deligo.review.repository;

import com.example.deligo.review.entity.Review;
import com.example.deligo.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> fingByOrder_StoreOrderByCreatedAtDesc(Store store); // 가게별 리뷰 최신순 조회
    List<Review> findByOrder_StoreAndRatingBetweenOrderByCreatedAtDesc(Store store, int minRating, int maxRating);
}
