package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {

    @Query("select rv from Reviews rv where rv.books.book_id = :book_id")
    List<Reviews> getAllBookReview(@Param("book_id") Long book_id);
}
