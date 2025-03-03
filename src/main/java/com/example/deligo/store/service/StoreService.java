package com.example.deligo.store.service;

import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.store.dto.StoreResponse;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    public List<StoreResponse> getStoresWithRatings() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(store -> {
                    double averageRating = reviewRepository.findAverageRatingByStoreId(store.getId());
                    return StoreResponse.from(store, averageRating);
                })
                .collect(Collectors.toList());
    }
}