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
                    "where (:category is null or b.categories.category_name = :category) " +
                    "and ((:minPrice is null and :maxPrice is null) or (b.discounted_price between :minPrice and :maxPrice)) " +
                    "and (:minDiscount is null or b.discount_percent >= :minDiscount) " +
                    "order by " +
                    "case when :sort = 'price_low' then b.discounted_price end asc, " +
                    "case when :sort = 'price_high' then b.discounted_price end desc"
    )
    public List<Books> filterBook(
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minDiscount") Double minDiscount,
            @Param("sort") String sort
    );
}
