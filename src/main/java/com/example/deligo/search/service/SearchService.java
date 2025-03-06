package com.example.deligo.search.service;

import com.example.deligo.common.dto.PaginationResponse;
import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.search.dto.PopularKeywordResponse;
import com.example.deligo.store.dto.response.StoreListResponse;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SearchService {

    private final StoreRepository storeRepository;
    private final SearchCacheService searchCacheService;

    public PaginationResponse<StoreListResponse> getStoresByKeyword(String keyword, Pageable pageable) {
        validateKeyword(keyword);
        searchCacheService.updateSearchKeywordCount(keyword);

        Page<Store> matchingStores = storeRepository.findByNameContaining(keyword, pageable);
        return new PaginationResponse<>(
                matchingStores.map(StoreListResponse::from)
        );
    }

    public PopularKeywordResponse getPopularSearchKeywords(int topN) {
        List<PopularKeywordResponse.PopularKeyword> popularKeywords
                = searchCacheService.fetchPopularSearchKeywords(topN);

        return new PopularKeywordResponse(LocalDateTime.now(), popularKeywords);
    }

    private void validateKeyword(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "검색어를 입력해주세요.");
        }
    }
}
