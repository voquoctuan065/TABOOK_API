package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.mapper.impl.BookMapper;
import com.tacm.tabooksapi.repository.BookRepository;
import com.tacm.tabooksapi.repository.CategoriesRepository;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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
//    @Override
//    public Page<Books> getAllBook(String category, Double minPrice, Double maxPrice, Double minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        List<Books> books = bookRepository.filterBook(category, minPrice, maxPrice, minDiscount, sort);
//
//        if (stock != null) {
//            if(stock.equals("in_stock")) {
//                books = books.stream().filter(b -> b.getStockQuantity()>0).collect(Collectors.toList());
//            }
//            else if(stock.equals("out_of_stock")) {
//                books = books.stream().filter(b -> b.getStockQuantity()<1).collect(Collectors.toList());
//            }
//        }
//        int startIndex = (int)pageable.getOffset();
//        int endIndex = Math.min(startIndex + pageable.getPageSize(), books.size());
//
//        List<Books> pageContent = books.subList(startIndex, endIndex);
//        Page<Books> filteredBooks = new PageImpl<>(pageContent, pageable, books.size());
//        return filteredBooks;
//    }


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

}
