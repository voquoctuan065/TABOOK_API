package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Ratings, Long> {

    @Query("select r from Ratings r where r.books.bookId = :book_id")
    List<Ratings> getAllBookRating(@Param("book_id") Long book_id);
}
