package com.example.deligo.store.service;

import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.store.dto.StoreResponse;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                    Optional<Double> optionalAverage = reviewRepository.findAverageRatingByStoreId(store.getId());
                    double averageRating = optionalAverage.orElse(0.0);
                    return StoreResponse.from(store, averageRating);
                })
                .collect(Collectors.toList());
    }
}