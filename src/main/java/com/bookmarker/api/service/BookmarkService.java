package com.bookmarker.api.service;

import com.bookmarker.api.domain.Bookmark;
import com.bookmarker.api.domain.BookmarkRepository;
import com.bookmarker.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository repository;
    private final BookmarkMapper mapper;

    /**
     * Bookmark 목록 조회
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    public BookmarksDTO<?> getBookmarks(Integer page) {
        // JPA Paging은 0부터 시작!
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "id");

//        Page<Bookmark> => Page<BookmarksDTO> 방법
//        1. mapper를 통한 Projection 후 호출
//        Page<BookmarkDTO> bookmarkPage = repository.findAll(pageable)
//                // lambda 식
//                //.map(bookmark -> mapper.toDTO(bookmark));
//                // 메소드 레퍼런스 method reference
//                .map(mapper::toDTO);

         //2. custom query method 호출
        Page<BookmarkDTO> bookmarkPage = repository.findBookmarks(pageable);
        return new BookmarksDTO(bookmarkPage);
    }

    /**
     * Bookmark 검색
     * @param query
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    public BookmarksDTO<?> searchBookmarks(String query, Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
        //1. 기존 메소드
        // Page<BookmarkDTO> bookmarkPage = repository.searchBookmarks(query, pageable);

        //2. 1번 메소드 대체하는 JPA Query method
        /* Page<BookmarkDTO> bookmarkPage = repository.findByTitleContainsIgnoreCase(query, pageable);
        return new BookmarksDTO(bookmarkPage);*/

        //3. 1번 메소드 대체하는 BookmarkVM I/F Injection
        Page<BookmarkVM> bookmarkPage = repository.findByTitleContainingIgnoreCase(query, pageable);
        return new BookmarksDTO<>(bookmarkPage);
    }

    /**
     * Bookmark 등록
     * @param request
     * @return
     */
    public BookmarkDTO createBookmark(CreateBookmarkRequest request) {
        Bookmark bookmark = new Bookmark(request.getTitle(), request.getUrl(), Instant.now());
        Bookmark savedBookmark = repository.save(bookmark);
        return mapper.toDTO(savedBookmark);
    }
}