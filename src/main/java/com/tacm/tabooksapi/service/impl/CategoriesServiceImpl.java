package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.repository.CategoriesRepository;
import com.tacm.tabooksapi.service.CategoriesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    private CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesServiceImpl(CategoriesRepository categoriesRepository, ModelMapper modelMapper) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<Categories> findAll() {
        return StreamSupport.stream(categoriesRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Categories create(Categories categories) {
        categories.setCreatedAt(LocalDateTime.now());
        return categoriesRepository.save(categories);
    }

    //--------------------------- Get Page Category ------------------//
    @Override
    public Page<Categories> getAllCategorires(int page, int size) {
        Sort sortInfo = Sort.by(Sort.Direction.DESC, "categoryId");
        Pageable pageable = PageRequest.of(page, size, sortInfo);
        return categoriesRepository.findAll(pageable);
    }
    //--------------------------- End Page Category ------------------//

    //--------------------------- Search Page Category ------------------//
    @Override
    public Page<Categories> searchCategoriesByName(String category_name, Pageable pageable) {
        return categoriesRepository.findByCategoryNameContainingIgnoreCase(category_name, pageable);
    }
    //--------------------------- End Search Page Category ------------------//

    //--------------------------- Delete Category By Id ---------------------//
    @Override
    public void deleteById(Long categoryId) {
        categoriesRepository.deleteById(categoryId);
    }
    //--------------------------- End Delete Category By Id ---------------------//

    //--------------------------- Update Category ---------------------//
    @Override
    public Categories updateCategory(Categories categories, Long categoryId) throws ApiException {
        Categories foundedCategory = findCategoryById(categoryId);
        foundedCategory.setCategoryName(categories.getCategoryName());
        foundedCategory.setParentCategory(categories.getParentCategory());
        foundedCategory.setUpdatedAt(LocalDateTime.now());
        return categoriesRepository.save(foundedCategory);
    }
    //--------------------------- End Update Category ---------------------//

    //--------------------------- Find Category By Id ---------------------//
    @Override
    public Categories findCategoryById(Long categoryId) throws ApiException {
        Optional<Categories> categories = categoriesRepository.findById(categoryId);
        if(categories.isPresent()) {
            return categories.get();
        }
        throw new ApiException("Không tìm thấy thể loại với id + " + categoryId, HttpStatus.NOT_FOUND);
    }
    //--------------------------- End Find Category By Id ---------------------//
}