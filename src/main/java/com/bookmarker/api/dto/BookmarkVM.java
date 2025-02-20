package com.bookmarker.api.dto;

import java.time.Instant;

public interface BookmarkVM {
    // ViewModel 역할을 하는 I/F
    Long getId();
    String getTitle();
    String getUrl();
    Instant getCreatedAt();
}