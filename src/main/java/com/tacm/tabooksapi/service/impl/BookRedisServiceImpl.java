package com.tacm.tabooksapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacm.tabooksapi.domain.dto.BooksDto;
import com.tacm.tabooksapi.domain.dto.BooksPageDto;
import com.tacm.tabooksapi.domain.dto.CategoriesDto;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.service.BookRedisService;
import com.tacm.tabooksapi.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BookRedisServiceImpl implements BookRedisService {

    private static final String BOOKS_CACHE_PREFIX = "books";
    private static final Logger logger = LoggerFactory.getLogger(BookRedisServiceImpl.class);
    private RedisTemplate<String, Object> redisTemplate;
    private ObjectMapper redisObjectMapper;
    private BookService bookService;

    private static final long expirationTimeInSeconds = 1800;

    @Autowired
    public BookRedisServiceImpl(RedisTemplate<String, Object> redisTemplate,
                                ObjectMapper redisObjectMapper,
                                BookService bookService) {
        this.redisTemplate = redisTemplate;
        this.redisObjectMapper = redisObjectMapper;
        this.bookService = bookService;
    }

    @Override
    public BooksPageDto getAllBooks(PageRequest pageRequest) throws JsonProcessingException {
        String cacheKey = generateCacheKey(pageRequest);
        String json = (String) redisTemplate.opsForValue().get(cacheKey);
        List<BooksDto> booksDtoList = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<BooksDto>>(){}) : null;

        if(booksDtoList != null) {
            long totalBooksInDatabase = bookService.getTotalBooks();
            int totalPages = (int) Math.ceil((double) totalBooksInDatabase / pageRequest.getPageSize());

            return new BooksPageDto(booksDtoList, totalPages);
        } else {
            return null;
        }

    }


    @Override
    public void saveAllBook(List<BooksDto> booksDtoList, PageRequest pageRequest) {
        String cacheKey = generateCacheKey(pageRequest);

        try {
            String json = redisObjectMapper.writeValueAsString(booksDtoList);
            redisTemplate.opsForValue().set(cacheKey, json, expirationTimeInSeconds, TimeUnit.SECONDS);
        } catch(Exception e) {
            logger.info(String.valueOf(e));
        }
    }

    @Override
    public BooksPageDto searchingBookByName(String keyword, PageRequest pageRequest) throws JsonProcessingException {
        String cacheKey = generateSearchingCacheKey(keyword, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(cacheKey);

        List<BooksDto> booksDtoList = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<BooksDto>>(){}) : null;
        if(booksDtoList != null) {
            long totalBookSearching = bookService.getTotalBookSearching(keyword);
            int totalPages = (int) Math.ceil((double) totalBookSearching / pageRequest.getPageSize());

            return new BooksPageDto(booksDtoList, totalPages);
        } else {
            return null;
        }
    }

    @Override
    public void saveSearchingBook(List<BooksDto> booksDtoList, String keyword, PageRequest pageRequest) {
        String cacheKey = generateSearchingCacheKey(keyword, pageRequest);
        try {
            String json = redisObjectMapper.writeValueAsString(booksDtoList);
            redisTemplate.opsForValue().set(cacheKey, json, expirationTimeInSeconds, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public BooksPageDto getBookByPathName(String pathName, PageRequest pageRequest) throws JsonProcessingException {
        String cacheKey = generateBookByPathName(pathName, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(cacheKey);

        List<BooksDto> booksDtoList = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<BooksDto>>(){}) : null;
        if(booksDtoList != null) {
            long totalBookPathName = bookService.getTotalBookPathName(pathName);
            int totalPages = (int) Math.ceil((double) totalBookPathName / pageRequest.getPageSize());

            return new BooksPageDto(booksDtoList, totalPages);
        } else {
            return null;
        }
    }

    @Override
    public void saveBookByPathName(List<BooksDto> booksDtoList, String pathName,PageRequest pageRequest) {
        String cacheKey = generateBookByPathName(pathName, pageRequest);
        try {
            String json = redisObjectMapper.writeValueAsString(booksDtoList);
            redisTemplate.opsForValue().set(cacheKey, json, expirationTimeInSeconds, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BooksDto findBookById(Long bookId) throws JsonProcessingException {
        String cacheKey = generateBookByIdCacheKey(bookId);
        String json = (String) redisTemplate.opsForValue().get(cacheKey);

        BooksDto booksDto = json != null ? redisObjectMapper.readValue(json, BooksDto.class) : null;
        return booksDto;
    }

    @Override
    public void saveBookById(BooksDto booksDto, Long bookId) {
        String cacheKey = generateBookByIdCacheKey(bookId);
        try {
             String json = redisObjectMapper.writeValueAsString(booksDto);
             redisTemplate.opsForValue().set(cacheKey, json, expirationTimeInSeconds, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BooksDto> findLatestBooks() throws JsonProcessingException {
        String cacheKey = generateLatestBookCacheKey();
        String json = (String) redisTemplate.opsForValue().get(cacheKey);
        List<BooksDto> booksDtoList = json != null ?  redisObjectMapper.readValue(json, new TypeReference<List<BooksDto>>(){}) : null;

        return booksDtoList;
    }

    @Override
    public void saveLatestBooks(List<BooksDto> booksDtoList) {
        String cacheKey = generateLatestBookCacheKey();
        try {
            String json = redisObjectMapper.writeValueAsString(booksDtoList);
            redisTemplate.opsForValue().set(cacheKey, json, expirationTimeInSeconds, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    private String generateCacheKey(PageRequest pageRequest) {
        // Generate a unique cache key based on page request parameters
        return String.format("%s_%d_%d", BOOKS_CACHE_PREFIX, pageRequest.getPageNumber(), pageRequest.getPageSize());
    }

    private String generateSearchingCacheKey(String keyword, PageRequest pageRequest) {
        return String.format("%s_%s_%d_%d", BOOKS_CACHE_PREFIX, keyword, pageRequest.getPageNumber(), pageRequest.getPageSize());
    }

    private String generateBookByPathName(String pathName, PageRequest pageRequest) {
        return String.format("%s_%s_%d_%d", BOOKS_CACHE_PREFIX, pathName, pageRequest.getPageNumber(), pageRequest.getPageSize());
    }

    private String generateBookByIdCacheKey(Long bookId) {
        return String.format("%s_%d", BOOKS_CACHE_PREFIX, bookId);
    }

    private String generateLatestBookCacheKey() {
        return String.format("%s", "latest_book");
    }
}
