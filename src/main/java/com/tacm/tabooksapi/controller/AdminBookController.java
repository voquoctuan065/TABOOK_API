package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.ApiResponse;
import com.tacm.tabooksapi.domain.dto.BooksDto;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.mapper.impl.BookMapper;
import com.tacm.tabooksapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/book")
public class AdminBookController {

    private BookService bookService;
    private BookMapper bookMapper;
    @Autowired
    public AdminBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/")
    public ResponseEntity<Books> createBooks(@RequestBody BooksDto booksDto) {
        Books books = bookMapper.mapFrom(booksDto);
        Books savedBooks = bookService.createBook(books);

        return new ResponseEntity<>(savedBooks, HttpStatus.CREATED);
    }

    @DeleteMapping("/{book_id}/delete")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long book_id) throws ProductException {
        bookService.delete(book_id);
        ApiResponse res = new ApiResponse();
        res.setMessage("book deleted successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Books>> findAllBook() {
        List<Books> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PutMapping("/{book_id}/update")
    public ResponseEntity<Books> updateBook(@RequestBody Books req, @PathVariable Long book_id) throws ProductException {
        Books books = bookService.updateBook(book_id, req);
        return new ResponseEntity<>(books, HttpStatus.CREATED);
    }

}
