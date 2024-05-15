package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.dto.BooksDto;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.exception.ProductException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookService {

//    Books createBook(Books books);

//    List<Books> findAll();

//    Optional<Books> findOne(Long id);

//    boolean isExists(Long id);

//    void delete(Long id) throws ProductException;

//    Books updateBook(Long id, Books books) throws ProductException;

    Books findBookById(Long id) throws ProductException;
//    List<Books> findBookByCategory(String category);

    Page<Books> filterBooks(String pathName, Double minPrice, Double maxPrice
    , Long nxbId, String sort, Integer pageNumber, Integer pageSize);
    Page<Books> getPageBook(int page, int size);

    Page<Books> searchBookByName(String bookTitle, Pageable pageable);

    Books addBook(Books book) throws ApiException, IOException;

    void deleteBook(Long bookId);

    Books updateBook(Books books, Long bookId) throws ProductException;

    Page<Books> getBookByPathName(String pathName, int page, int size) throws ApiException;

    List<Books> findAll();

    Long getTotalBooks();

    long getTotalBookSearching(String keyword);

    long getTotalBookPathName(String pathName);

    List<Books> findLatestBooks();

    List<Books> findFavoriteBooks();

    List<Books> findHotBooks();

    Page<Books> findOutOfStockBook(Pageable pageable);
}
