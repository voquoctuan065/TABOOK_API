package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.repository.BookRepository;
import com.tacm.tabooksapi.repository.CategoriesRepository;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private UserService userService;
    private CategoriesRepository categoriesRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, UserService userService, CategoriesRepository categoriesRepository) {
       this.bookRepository = bookRepository;
       this.userService = userService;
       this.categoriesRepository = categoriesRepository;
    }
    @Override
    public Books createBook(Books books) {
        books.setCreated_at(LocalDateTime.now());
        return bookRepository.save(books);
    }

    @Override
    public List<Books> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Books> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean isExists(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Books updateBook(Long id, Books books) throws ProductException {
        Books foundedBook = findBookById(id);
        if(foundedBook.getStock_quantity() != 0) {
            foundedBook.setStock_quantity(foundedBook.getStock_quantity());
        }
        return bookRepository.save(foundedBook);
    }

    @Override
    public Books findBookById(Long id) throws ProductException {
        Optional<Books> books = bookRepository.findById(id);
        if(books.isPresent()) {
            return books.get();
        }
        throw new ProductException("Không tìm thấy sách với id - " + id);
    }

    @Override
    public List<Books> findBookByCategory(String category) {
        return null;
    }

    @Override
    public Page<Books> getAllBook(String category, Double minPrice, Double maxPrice, Double minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Books> books = bookRepository.filterBook(category, minPrice, maxPrice, minDiscount, sort);

        if (stock != null) {
            if(stock.equals("in_stock")) {
                books = books.stream().filter(b -> b.getStock_quantity()>0).collect(Collectors.toList());
            }
            else if(stock.equals("out_of_stock")) {
                books = books.stream().filter(b -> b.getStock_quantity()<1).collect(Collectors.toList());
            }
        }
        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), books.size());

        List<Books> pageContent = books.subList(startIndex, endIndex);
        Page<Books> filteredBooks = new PageImpl<>(pageContent, pageable, books.size());
        return filteredBooks;
    }

    @Override
    public List<Books> searchBook(String q) {
         return null;
    }
}
