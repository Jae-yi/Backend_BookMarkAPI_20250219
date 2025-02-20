package com.bookmarker.api.controller;

import com.bookmarker.api.dto.BookmarkDTO;
import com.bookmarker.api.dto.BookmarksDTO;
import com.bookmarker.api.dto.CreateBookmarkRequest;
import com.bookmarker.api.service.BookmarkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    /**
     * Bookmark 목록 및 검색
     * @param page
     * @param query
     * @return
     */
    @GetMapping
    public BookmarksDTO getBookmarks(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "query", defaultValue = "") String query) {

        // query param (검색어) 비어있으면 그냥 리스트만 호출
        if(query == null || query.isBlank()) {
            return bookmarkService.getBookmarks(page);
        }
        return bookmarkService.searchBookmarks(query, page);
    }

    /**
     * Bookmark 등록
     * @param request
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    // @Valid : CreateBookmarkRequest에 선언한 어노테이션에 따라 잘 들어왔는지 DataBinding을 체크해주는 Validator 호출
    public BookmarkDTO createBookmark(@RequestBody @Valid CreateBookmarkRequest request) {
        return bookmarkService.createBookmark(request);
    }
}