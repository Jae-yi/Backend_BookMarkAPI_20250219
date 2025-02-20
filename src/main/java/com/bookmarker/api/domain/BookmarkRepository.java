package com.bookmarker.api.domain;

import com.bookmarker.api.dto.BookmarkDTO;
import com.bookmarker.api.dto.BookmarkVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
//    1. class-based JPA Projection
//    @Query("""
//    select new com.bookmarker.api.dto.BookmarkDTO(b.id, b.title, b.url, b.createdAt) from Bookmark b
//    """)
//    Page<BookmarkDTO> findBookmarks(Pageable pageable);

//   2, hypersistence 의존성 활용
    @Query("""
    select new BookmarkDTO(b.id, b.title, b.url, b.createdAt) from Bookmark b
    """)
    Page<BookmarkDTO> findBookmarks(Pageable pageable);

    @Query("""
    select new BookmarkDTO(b.id, b.title, b.url, b.createdAt) from Bookmark b
    where lower(b.title) like lower(concat('%', :query, '%'))
    """)
    Page<BookmarkDTO> searchBookmarks(String query, Pageable pageable);

    // 2.기존 searchBookmarks를 대체하는 JPA Method
    // Method naming 규칙에 따라 작성
    Page<BookmarkDTO> findByTitleContainsIgnoreCase(String query, Pageable pageable);

    // 3. BookmarkVM I/F Injection
    Page<BookmarkVM> findByTitleContainingIgnoreCase(String query, Pageable pageable);
}