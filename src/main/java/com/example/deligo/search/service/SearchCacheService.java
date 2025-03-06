package com.example.deligo.search.service;

import com.example.deligo.search.dto.PopularKeywordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SearchCacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String POPULAR_KEYWORDS_KEY = "popularKeywords";
    private static final int POPULAR_KEYWORDS_MAX_SIZE = 10;

    public void updateSearchKeywordCount(String keyword) {
        redisTemplate.opsForZSet().incrementScore(POPULAR_KEYWORDS_KEY, keyword, 1);

        Long size = redisTemplate.opsForZSet().zCard(POPULAR_KEYWORDS_KEY); // 해당 키로 저장된 검색어 개수 확인

        // 저장 중인 키워드가 POPULAR_KEYWORDS_MAX_SIZE 이상이 될 경우
        if (size != null && size > POPULAR_KEYWORDS_MAX_SIZE) {
            // 검색 횟수가 적은 키워드 삭제 처리
            redisTemplate.opsForZSet().removeRange(POPULAR_KEYWORDS_KEY, 0, size - POPULAR_KEYWORDS_MAX_SIZE -1);
        }
    }

    public List<PopularKeywordResponse.PopularKeyword> fetchPopularSearchKeywords(int topN) {
        Set<ZSetOperations.TypedTuple<String>> result = redisTemplate.opsForZSet()
                .reverseRangeWithScores(POPULAR_KEYWORDS_KEY, 0, topN-1);

        if (result == null || result.isEmpty()) {
            return Collections.emptyList();
        }
        return result.stream()
                .map(tuple -> new PopularKeywordResponse.PopularKeyword(
                        tuple.getValue(),
                        Optional.ofNullable(tuple.getScore()).orElse(0.0).longValue()
                ))
                .toList();
    }
}
