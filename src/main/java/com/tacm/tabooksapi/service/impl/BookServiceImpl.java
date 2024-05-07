package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.mapper.impl.BookMapper;
import com.tacm.tabooksapi.repository.BookRepository;
import com.tacm.tabooksapi.repository.CategoriesRepository;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private UserService userService;
    private CategoriesRepository categoriesRepository;

    private BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, UserService userService,
                           CategoriesRepository categoriesRepository, BookMapper bookMapper) {
       this.bookRepository = bookRepository;
       this.userService = userService;
       this.categoriesRepository = categoriesRepository;
       this.bookMapper = bookMapper;
    }

//    @Override
//    public Books createBook(Books books) {
//        books.setCreatedAt(LocalDateTime.now());
//        return bookRepository.save(books);
//    }
//
//    @Override
//    public List<Books> findAll() {
//        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
//    }
//
//    @Override
//    public Optional<Books> findOne(Long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean isExists(Long id) {
//        return bookRepository.existsById(id);
//    }
//
//    @Override
//    public void delete(Long id) {
//        bookRepository.deleteById(id);
//    }
//
//    @Override
//    public Books updateBook(Long id, Books books) throws ProductException {
//        Books foundedBook = findBookById(id);
//        if(foundedBook.getStockQuantity() != 0) {
//            foundedBook.setStockQuantity(foundedBook.getStockQuantity());
//        }
//        return bookRepository.save(foundedBook);
//    }
//


//    @Override
//    public List<Books> findBookByCategory(String category) {
//        return null;
//    }
//
    @Override
    public Page<Books> filterBooks(String pathName, Double minPrice, Double maxPrice, Long nxbId, String sort, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Books> books ;
        if(nxbId != null) {
            books = bookRepository.filterBook(pathName,  minPrice, maxPrice, nxbId, sort);
        } else {
            books = bookRepository.filterBook(pathName,  minPrice, maxPrice, sort);
        }
        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), books.size());

        if (startIndex >= books.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<Books> pageContent = books.subList(startIndex, endIndex);
        Page<Books> filteredBooks = new PageImpl<>(pageContent, pageable, books.size());
        return filteredBooks;
    }


    //------------------------------- Find Book By Id -------------------------------------//
    @Override
    public Books findBookById(Long id) throws ProductException {
        Optional<Books> books = bookRepository.findById(id);
        if(books.isPresent()) {
            return books.get();
        }
        throw new ProductException("Không tìm thấy sách với id - " + id);
    }
    //------------------------------- End Find Book By Id -------------------------------------//

    //------------------------------- Get Page Book -------------------------------------//
    @Override
    public Page<Books> getPageBook(int page, int size) {
        Sort sortInfo = Sort.by(Sort.Direction.DESC, "bookId");
        Pageable pageable = PageRequest.of(page, size, sortInfo);
        return bookRepository.findAll(pageable);
    }
    //------------------------------- End Get Page Book -------------------------------------//

    //------------------------------- Search Book By Name -------------------------------------//

    @Override
    public Page<Books> searchBookByName(String bookTitle, Pageable pageable) {
        return bookRepository.findByBookTitle(bookTitle, pageable);
    }

    //------------------------------- End Search Book By Name -------------------------------------//

    //------------------------------- Add New Book -------------------------------------//

    @Override
    public Books addBook(Books book ) throws ApiException {
        book.setCreatedAt(LocalDateTime.now());
        book.setHot(false);
        return bookRepository.save(book);
    }

    //------------------------------- End Add New Book -------------------------------------//

    //------------------------------------ Delete Book -------------------------------------//
    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }
    //------------------------------------ End Delete Book -------------------------------------//

    //------------------------------------ Update Book -------------------------------------//

    @Override
    public Books updateBook(Books books, Long bookId) throws ProductException {
        Books foundedBook = findBookById(bookId);
        foundedBook.setBookTitle(books.getBookTitle());
        foundedBook.setBookImage(books.getBookImage());
        foundedBook.setBookDescription(books.getBookDescription());
        foundedBook.setAuthorName(books.getAuthorName());
        foundedBook.setBookPrice(books.getBookPrice());
        foundedBook.setStockQuantity(books.getStockQuantity());
        foundedBook.setYearProduce(books.getYearProduce());
        foundedBook.setHot(books.isHot());
        foundedBook.setNxbs(books.getNxbs());
        foundedBook.setCategories(books.getCategories());
        foundedBook.setUpdatedAt(LocalDateTime.now());
        return bookRepository.save(foundedBook);
    }

    //------------------------------------ End Update Book -------------------------------------//

    //-------------------------------------- Get Book By Category Path Name ------------------------------------//

    @Override
    public Page<Books> getBookByPathName(String pathName, int page, int size) throws ApiException {

        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findByCategoriesPathName(pathName, pageable);
    }
    //-------------------------------------- End Get Book By Category Path Name ---------------------------------//

    @Override
    public List<Books> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Long getTotalBooks() {
        return bookRepository.count();
    }

    @Override
    public long getTotalBookSearching(String keyword) {
        return bookRepository.countByBookTitleContainingIgnoreCase(keyword);
    }

    @Override
    public long getTotalBookPathName(String pathName) {
        return bookRepository.countByCategoriesPathName(pathName);
    }

    @Override
    public List<Books> findLatestBooks() {
        return bookRepository.findLatestBooks();
    }

    @Override
    public List<Books> findFavoriteBooks() {
        return bookRepository.findDistinctBookWithRatingGreaterThanEqual4();
    }

    @Override
    public List<Books> findHotBooks() {
        return bookRepository.findByHotTrue();
    }
}
