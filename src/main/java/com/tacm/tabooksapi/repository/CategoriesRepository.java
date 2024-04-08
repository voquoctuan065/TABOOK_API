package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    @Query("SELECT c FROM Categories c WHERE LOWER(c.category_name) LIKE LOWER(CONCAT(:keyword, '%')) OR " +
            "LOWER(c.category_name) LIKE LOWER(:keyword) OR " +
            "LOWER(c.category_name) LIKE LOWER(CONCAT('%', :keyword)) OR " +
            "LOWER(c.category_name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Categories> findByCategoryNameContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
}
