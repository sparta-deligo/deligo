package com.example.deligo.search.controller;

import com.example.deligo.common.dto.PaginationResponse;
import com.example.deligo.search.dto.PopularKeywordResponse;
import com.example.deligo.search.service.SearchService;
import com.example.deligo.store.dto.response.StoreListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/store/search")
    public PaginationResponse<StoreListResponse> searchStoresByKeyword(
            @RequestParam(required = true) String keyword,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return searchService.getStoresByKeyword(keyword, pageable);
    }

    @GetMapping("/store/search/popular")
    public ResponseEntity<PopularKeywordResponse> getPopularSearchKeywords(
            @RequestParam(defaultValue = "10") int topN
    ) {
        return new ResponseEntity<>(searchService.getPopularSearchKeywords(topN), HttpStatus.OK);
    }
}