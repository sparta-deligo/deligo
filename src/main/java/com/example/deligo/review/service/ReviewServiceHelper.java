package com.example.deligo.review.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceHelper {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void updateStoreAverageRating(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ExceptionType.STORE_NOT_FOUND));

        double newAverageRating = reviewRepository.findAverageRatingByStoreId(storeId).orElse(0.0);
        store.updateAverageRating(newAverageRating);
    }
}