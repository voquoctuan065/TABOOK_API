package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.exception.ProductException;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Books createBook(Books books);

    List<Books> findAll();

    Optional<Books> findOne(Long id);

    boolean isExists(Long id);

    void delete(Long id) throws ProductException;

    Books updateBook(Long id, Books books) throws ProductException;

    Books findBookById(Long id) throws ProductException;

    List<Books> findBookByCategory(String category);

    Page<Books> getAllBook(String category, Double minPrice, Double maxPrice, Double minDiscount
    , String sort, String stock, Integer pageNumber, Integer pageSize);

    List<Books> searchBook(String q);
}
