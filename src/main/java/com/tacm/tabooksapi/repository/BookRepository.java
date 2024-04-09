package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
    @Query(
            "select b from Books b " +
                    "where (:category is null or b.categories.categoryName = :category) " +
                    "and ((:minPrice is null and :maxPrice is null) or (b.discountedPrice between :minPrice and :maxPrice)) " +
                    "and (:minDiscount is null or b.discountPercent >= :minDiscount) " +
                    "order by " +
                    "case when :sort = 'price_low' then b.discountedPrice end asc, " +
                    "case when :sort = 'price_high' then b.discountedPrice end desc"
    )
    public List<Books> filterBook(
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minDiscount") Double minDiscount,
            @Param("sort") String sort
    );
}
