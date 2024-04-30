package com.tacm.tabooksapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tacm.tabooksapi.domain.dto.BooksDto;
import com.tacm.tabooksapi.domain.dto.BooksPageDto;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.mapper.impl.BookMapper;
import com.tacm.tabooksapi.service.BookRedisService;
import com.tacm.tabooksapi.service.BookService;
import io.netty.util.internal.ObjectCleaner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/book")
@CrossOrigin("*")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private BookService bookService;
    private BookMapper bookMapper;

    private BookRedisService bookRedisService;
    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper, BookRedisService bookRedisService) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.bookRedisService = bookRedisService;
    }

//    @GetMapping("/filter")
//    public BooksPageDto getFilterBook(@RequestParam String pathName,
//                                      @RequestParam(required = false) Double minPrice,
//                                      @RequestParam(required = false) Double maxPrice,
//                                      @RequestParam(required = false) Long nxbId,
//                                      @RequestParam(required = false) String sort,
//                                      @RequestParam(defaultValue = "0") int page,
//                                      @RequestParam(defaultValue = "10") int size) {
//
//        Page<Books> booksPage = bookService.filterBooks(pathName, minPrice, maxPrice, nxbId, sort, page, size);
//        List<BooksDto> booksDtoList = booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
//        int totalPages = booksPage.getTotalPages();
//        return new BooksPageDto(booksDtoList, totalPages);
//    }

//    @GetMapping("/page")
//    public BooksPageDto getPageBook(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//
//            Page<Books> booksPage = bookService.getPageBook(page, size);
//            List<BooksDto> booksDtoList = booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
//            int totalPages = booksPage.getTotalPages();
//
//            return new BooksPageDto(booksDtoList, totalPages);
//    }

//    @GetMapping(path = "/c")
//    public BooksPageDto getBookByPathName(@RequestParam String pathName,
//                                          @RequestParam(defaultValue = "0") int page,
//                                          @RequestParam(defaultValue = "10") int size) throws ApiException {
//        Page<Books> booksPage = bookService.getBookByPathName(pathName, page, size);
//        List<BooksDto> booksDtoList = booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
//        int totalPages = booksPage.getTotalPages();
//        return new BooksPageDto(booksDtoList, totalPages);
//    }
    @GetMapping("/book/all")
    public List<BooksDto> getAll() {
        List<Books> books = bookService.findAll();
        return books.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }

    //---------------------------------------------------- Caching ---------------------------------------------------------//
    @GetMapping("/page")
    public BooksPageDto getAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("bookId").descending());
        logger.info(String.format("page = %d, size = %d", page, size));

        BooksPageDto booksPageDto = bookRedisService.getAllBooks(pageRequest);

        if(booksPageDto == null) {
            Page<Books> bookPage = bookService.getPageBook(page, size);
            int totalPages = bookPage.getTotalPages();
            List<BooksDto> booksDtoList =  bookPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
            bookRedisService.saveAllBook(booksDtoList, pageRequest);
            return new BooksPageDto(booksDtoList, totalPages);
        } else  {
            return booksPageDto;
        }

    }


    @GetMapping("/search")
    public BooksPageDto searchingBookByName(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("bookId").descending());
        logger.info(String.format("keyword = %s, page = %d, size = %d", keyword,page, size));
        BooksPageDto booksPageDto = bookRedisService.searchingBookByName(keyword, pageRequest);
        if(booksPageDto == null) {
            Page<Books> booksPage =  bookService.searchBookByName(keyword, PageRequest.of(page, size));
            int totalPages = booksPage.getTotalPages();
            List<BooksDto> booksDtoList =  booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());

            bookRedisService.saveSearchingBook(booksDtoList, keyword, pageRequest);
            return new BooksPageDto(booksDtoList, totalPages);
        } else {
            return booksPageDto;
        }
    }

    @GetMapping(path = "/c")
    public BooksPageDto getBookByPathName(@RequestParam String pathName,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) throws ApiException, JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("bookId").descending());
        logger.info(String.format("pathName = %s, page = %d, size = %d", pathName, page, size));
        BooksPageDto booksPageDto = bookRedisService.getBookByPathName(pathName, pageRequest);
        if(booksPageDto == null) {
            Page<Books> booksPage = bookService.getBookByPathName(pathName, page, size);
            List<BooksDto> booksDtoList = booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
            int totalPages = booksPage.getTotalPages();
            bookRedisService.saveBookByPathName(booksDtoList, pathName,pageRequest);
            return new BooksPageDto(booksDtoList, totalPages);
        } else {
            return booksPageDto;
        }
    }

    @GetMapping(path = "/{bookId}")
    public BooksDto findBookById(@PathVariable("bookId") Long bookId) throws ProductException, JsonProcessingException {
        BooksDto booksDto = bookRedisService.findBookById(bookId);
        if(booksDto == null) {
            Books books = bookService.findBookById(bookId);
            booksDto = bookMapper.mapTo(books);
            bookRedisService.saveBookById(booksDto, bookId);
        }
        return booksDto;
    }

    @GetMapping("/filter")
    public BooksPageDto getFilterBook(@RequestParam String pathName,
                                      @RequestParam(required = false) Double minPrice,
                                      @RequestParam(required = false) Double maxPrice,
                                      @RequestParam(required = false) Long nxbId,
                                      @RequestParam(required = false) String sort,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {

        Page<Books> booksPage = bookService.filterBooks(pathName, minPrice, maxPrice, nxbId, sort, page, size);
        List<BooksDto> booksDtoList = booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
        int totalPages = booksPage.getTotalPages();
        return new BooksPageDto(booksDtoList, totalPages);
    }


}
