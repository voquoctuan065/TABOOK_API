package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@CrossOrigin("*")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<Page<Books>> findBookByCategoryHandler(
        @RequestParam String category, @RequestParam Double minPrice,
        @RequestParam Double maxPrice, @RequestParam Double minDiscount,
        @RequestParam String sort, @RequestParam String stock, @RequestParam Integer pageNumber,
        @RequestParam Integer pageSize
    ) {
        Page<Books> res = bookService.getAllBook(category, minPrice, maxPrice, minDiscount,
                sort, stock, pageNumber, pageNumber);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Books> findBookByIdHandler(@PathVariable Long book_id) throws ProductException {
        Books books = bookService.findBookById(book_id);
        return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
    }
    

}
