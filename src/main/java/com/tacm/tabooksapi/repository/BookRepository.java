package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
    @Query(
            "SELECT b FROM Books b " +
                    "WHERE ((:minPrice IS NULL AND :maxPrice IS NULL) OR (b.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
                    "AND (:nxbId IS NULL OR b.nxbs.nxbId = :nxbId) " +
                    "ORDER BY " +
                    "CASE WHEN :sort = 'price_low' THEN b.discountedPrice END ASC, CASE WHEN :sort = 'price_high' THEN b.discountedPrice END DESC"
    )
    public List<Books> filterBook(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("nxbId") Long nxbId,
            @Param("sort") String sort
    );

    @Query(
            "SELECT b FROM Books b " +
                    "WHERE ((:minPrice IS NULL AND :maxPrice IS NULL) OR (b.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
                    "ORDER BY " +
                    "CASE WHEN :sort = 'price_low' THEN b.discountedPrice END ASC, CASE WHEN :sort = 'price_high' THEN b.discountedPrice END DESC"
    )
    public List<Books> filterBook(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("sort") String sort
    );

    @Query("SELECT b FROM Books b WHERE LOWER(b.bookTitle) LIKE LOWER(CONCAT(:bookTitle, '%')) OR " +
            "LOWER(b.bookTitle) LIKE LOWER(:bookTitle) OR " +
            "LOWER(b.bookTitle) LIKE LOWER(CONCAT('%', :bookTitle)) OR " +
            "LOWER(b.bookTitle) LIKE LOWER(CONCAT('%', :bookTitle, '%'))")
    Page<Books> findByBookTitle(@Param("bookTitle") String bookTitle, Pageable pageable);

    Page<Books> findByCategoriesPathName(String pathName, Pageable pageable);

    long countByBookTitleContainingIgnoreCase(String keyword);

    long countByCategoriesPathName(String pathName);

    @Query(value = "SELECT TOP 12 * FROM books ORDER BY created_at DESC", nativeQuery = true)
    List<Books> findLatestBooks();

    @Query("SELECT DISTINCT b FROM Books b INNER JOIN BooksRate br ON b.bookId = br.books.bookId  where br.rating >= 4")
    List<Books> findDistinctBookWithRatingGreaterThanEqual4();

    List<Books> findByHotTrue();

    Page<Books> findByStockQuantityEquals(int i, Pageable pageable);
}
