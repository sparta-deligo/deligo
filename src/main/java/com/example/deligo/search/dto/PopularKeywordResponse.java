package com.example.deligo.search.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PopularKeywordResponse {

    private LocalDateTime timestamp;
    private List<PopularKeyword> keywords;

    public PopularKeywordResponse(LocalDateTime timestamp, List<PopularKeyword> keywords) {
        this.timestamp = timestamp;
        this.keywords = keywords;
    }

    @Getter
    public static class PopularKeyword {
        private String keyword;
        private long count;

        public PopularKeyword(String keyword, long count) {
            this.keyword = keyword;
            this.count = count;
        }
    }
}
