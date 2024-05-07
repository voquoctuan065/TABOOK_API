package com.tacm.tabooksapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tacm.tabooksapi.domain.dto.*;
import com.tacm.tabooksapi.domain.entities.Books;
import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.exception.ProductException;
import com.tacm.tabooksapi.mapper.impl.BookMapper;
import com.tacm.tabooksapi.mapper.impl.CategoriesMapper;
import com.tacm.tabooksapi.mapper.impl.NXBsMapper;
import com.tacm.tabooksapi.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    private CategoriesService categoriesService;
    private CategoriesMapper categoriesMapper;
    private BookService bookService;
    private BookMapper bookMapper;
    private NXBsMapper nxbMapper;
    private NXBsService nxbService;

    private ImageService imageService;
    private CategoryRedisService categoryRedisService;
    private NXBsRedisService nxBsRedisService;
    @Autowired
    public AdminController(CategoriesService categoriesService, CategoriesMapper categoriesMapper,
                           BookService bookService, BookMapper bookMapper,
                           NXBsMapper nxbMapper, NXBsService nxbService,
                           ImageService imageService,
                           CategoryRedisService categoryRedisService,
                           NXBsRedisService nxBsRedisService) {
        this.categoriesService = categoriesService;
        this.categoriesMapper = categoriesMapper;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.nxbMapper = nxbMapper;
        this.nxbService = nxbService;
        this.imageService = imageService;
        this.categoryRedisService = categoryRedisService;
        this.nxBsRedisService = nxBsRedisService;
    }


    //------------------------------------------------- Category ------------------------------------------------//
    @GetMapping("/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoriesPageDto getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("categoryId").descending());

        CategoriesPageDto categoriesPageDto = categoryRedisService.getAllCategories(pageRequest);
        if(categoriesPageDto == null) {
            Page<Categories> categoriesPage = categoriesService.getAllCategorires(pageRequest);
            List<CategoriesDto> categoriesDtoList = categoriesPage.getContent().stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
            int totalPages = categoriesPage.getTotalPages();

            categoryRedisService.saveAllCategory(categoriesDtoList, pageRequest);
            return new CategoriesPageDto(categoriesDtoList, totalPages);
        } else {
            return categoriesPageDto;
        }

    }

    @GetMapping(path = "/category/list-category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CategoriesDto> listCategories() throws JsonProcessingException {
        List<CategoriesDto> categoriesDtoList = categoryRedisService.findAll();
        if(categoriesDtoList == null) {
            List<Categories> category = categoriesService.findAll();
            categoriesDtoList = category.stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
            categoryRedisService.saveAll(categoriesDtoList);
        }

        return categoriesDtoList;
    }


    @GetMapping("/category/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoriesPageDto searchCategoriesByName(@RequestParam String keyword,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Page<Categories> categoriesPage =  categoriesService.searchCategoriesByName(keyword, PageRequest.of(page, size));
        List<CategoriesDto> categoriesDtoList = categoriesPage.getContent().stream().map(categoriesMapper::mapTo).collect(Collectors.toList());
        int totalPages = categoriesPage.getTotalPages();
        return new CategoriesPageDto(categoriesDtoList, totalPages);
    }

    @PostMapping(path = "/category/add-category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CategoriesDto createCategories(@RequestBody CategoriesDto categoriesDto) {
        Categories categories = categoriesMapper.mapFrom(categoriesDto);
        Categories savedCategories = categoriesService.create(categories);
        return categoriesMapper.mapTo(savedCategories);
    }


    @DeleteMapping(path = "/category/delete/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategories(@PathVariable("categoryId") Long categoryId){
        categoriesService.deleteById(categoryId);
        ApiResponse res = new ApiResponse();
        res.setMessage("category deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/category/update/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoriesDto> updateCategory(@RequestBody CategoriesDto categoriesDto,
                                                     @PathVariable("categoryId") Long categoryId) throws ApiException {
        Categories categories = categoriesMapper.mapFrom(categoriesDto);
        Categories updatedCategories = categoriesService.updateCategory(categories, categoryId);

        return new ResponseEntity<>(categoriesMapper.mapTo(updatedCategories), HttpStatus.CREATED);
    }
    //------------------------------------------------- End Category ------------------------------------------------//

    //------------------------------------------------- Book ------------------------------------------------//

    @PostMapping("/book/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BooksDto> addNewBook(@Valid @RequestBody BooksDto booksDto) throws  ApiException {
        try {
            Books books = bookMapper.mapFrom(booksDto);
            Books savedBook = bookService.addBook(books);
            return new ResponseEntity<>(bookMapper.mapTo(savedBook), HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/book")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BooksPageDto getPageBook(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Books> booksPage = bookService.getPageBook(page, size);
        List<BooksDto> booksDtoList = booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
        int totalPages = booksPage.getTotalPages();

        return new BooksPageDto(booksDtoList, totalPages);
    }

    @GetMapping("/book/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BooksPageDto searchBookByName(@RequestParam String keyword,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Page<Books> booksPage =  bookService.searchBookByName(keyword, PageRequest.of(page, size));
        List<BooksDto> booksDtoList = booksPage.getContent().stream().map(bookMapper::mapTo).collect(Collectors.toList());
        int totalPages = booksPage.getTotalPages();
        return new BooksPageDto(booksDtoList, totalPages);
    }

    @DeleteMapping(path = "/book/delete/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable("bookId") Long bookId) throws ApiException {
        try {
            bookService.deleteBook(bookId);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Xoá sách thành công!");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(path = "/book/update/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BooksDto> updateBook(@RequestBody BooksDto booksDto,
                                                        @PathVariable("bookId") Long bookId)
            throws ApiException, ProductException {
        Books books = bookMapper.mapFrom(booksDto);
        Books updatedBook = bookService.updateBook(books, bookId);
        return new ResponseEntity<>(bookMapper.mapTo(updatedBook), HttpStatus.CREATED);
    }

    @GetMapping(path = "/book/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BooksDto findBookById(@PathVariable("bookId") Long bookId) throws ProductException {
        try {
            Books books = bookService.findBookById(bookId);
            return bookMapper.mapTo(books);
        } catch (ProductException e) {
            throw new RuntimeException(e);
        }
    }

    //------------------------------------------------- End Book ------------------------------------------------//

    //------------------------------------------------- NXBs ------------------------------------------------//
    @PostMapping(path = "/nxb/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NXBsDto> addNewBook(@RequestBody NXBsDto nxBsDto) throws  ApiException {
        try {
            NXBs nxbs = nxbMapper.mapFrom(nxBsDto);
            NXBs savedNxbs  = nxbService.addNXB(nxbs);
            return new ResponseEntity<>(nxbMapper.mapTo(savedNxbs), HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/nxb/list-nxb")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<NXBsDto> getListNXBs() throws JsonProcessingException {
        List<NXBsDto> nxBsDtoList = nxBsRedisService.findAll();
        if(nxBsDtoList == null) {
            List<NXBs> nxbList = nxbService.findAll();
            nxBsDtoList = nxbList.stream().map(nxbMapper::mapTo).collect(Collectors.toList());
            nxBsRedisService.saveAll(nxBsDtoList);
        }
        return nxBsDtoList;
    }

    @GetMapping(path = "/nxb/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<NXBsDto>> searchNxbByName (@RequestParam String keyword) throws JsonProcessingException {
        List<NXBsDto> nxBsDtoList = nxBsRedisService.searchNxbByName(keyword);

        if(nxBsDtoList == null) {
            List<NXBs> nxbList = nxbService.searchNxbByName(keyword);
            nxBsDtoList = nxbList.stream().map(nxbMapper::mapTo).collect(Collectors.toList());
            nxBsRedisService.saveNxbByName(nxBsDtoList, keyword);
        }
        return ResponseEntity.ok(nxBsDtoList);
    }

    @PutMapping(path = "/nxb/update/{nxbId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NXBsDto> updateNXB(@RequestBody NXBsDto nxBsDto,
                                               @PathVariable("nxbId") Long nxbId)
            throws ApiException {
        try {
            NXBs nxbs = nxbMapper.mapFrom(nxBsDto);
            NXBs savedNxbs = nxbService.updateNXB(nxbs, nxbId);
            return new ResponseEntity<>(nxbMapper.mapTo(savedNxbs), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/nxb/delete/{nxbId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteNXB(@PathVariable("nxbId") Long nxbId) throws ApiException {
        try {
            nxbService.deleteNXB(nxbId);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Xoá nhà xuất bản thành công!");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/nxb/{nxbId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public NXBsDto findNxbById(@PathVariable("nxbId") Long nxbId) throws ApiException {
        NXBsDto nxBsDto = nxBsRedisService.findNxbById(nxbId);
        if(nxBsDto == null) {
            NXBs nxBs = nxbService.findNxbById(nxbId);
            nxBsDto = nxbMapper.mapTo(nxBs);
            nxBsRedisService.saveNxbById(nxBsDto, nxbId);
        }
        return nxBsDto;
    }
    //------------------------------------------------- End NXBs ------------------------------------------------//

    //-------------------------------------------- Upload Image To Drive --------------------------------------//
    @PostMapping("/uploadToGoogleDrive")
    public Object handleFileUpload(@RequestParam ("image") MultipartFile file) throws IOException, GeneralSecurityException {
        if(file.isEmpty()) {
            return "File is empty";
        }
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        ImageRes imageRes = imageService.uploadImageToDrive(tempFile);
        System.out.println(imageRes);
        return imageRes;
    }
    //-------------------------------------------- End Upload Image To Drive --------------------------------------//

}
