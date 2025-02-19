package com.bookmarker.api.domain;

import com.bookmarker.api.dto.BookmarkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    // class-based JPA Projection
    @Query("""
    select new com.bookmarker.api.dto.BookmarkDTO(b.id, b.title, b.url, b.createdAt) from Bookmark b
    """)
    Page<BookmarkDTO> findBookmarks(Pageable pageable);

//    @Query("""
//    select new BookmarkDTO(b.id, b.title, b.url, b.createdAt) from Bookmark b
//    """)
//    Page<BookmarkDTO> findBookmarks(Pageable pageable);

    @Query("""
    select new com.bookmarker.api.dto.BookmarkDTO(b.id, b.title, b.url, b.createdAt) from Bookmark b
    where lower(b.title) like lower(concat('%', :query, '%'))
    """)
    Page<BookmarkDTO> searchBookmarks(String query, Pageable pageable);

    // 기존 searchBookmarks를 대체하는 JPA Method, naming 규칙에 따라 작성
    Page<BookmarkDTO> findByTitleContainsIgnoreCase(String query, Pageable pageable);
}