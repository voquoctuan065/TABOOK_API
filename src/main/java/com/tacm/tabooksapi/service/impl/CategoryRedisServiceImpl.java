package com.tacm.tabooksapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacm.tabooksapi.domain.dto.BooksDto;
import com.tacm.tabooksapi.domain.dto.BooksPageDto;
import com.tacm.tabooksapi.domain.dto.CategoriesDto;
import com.tacm.tabooksapi.domain.dto.CategoriesPageDto;
import com.tacm.tabooksapi.service.CategoriesService;
import com.tacm.tabooksapi.service.CategoryRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CategoryRedisServiceImpl implements CategoryRedisService {

    private static final Logger logger = LoggerFactory.getLogger(BookRedisServiceImpl.class);
    private RedisTemplate<String, Object> redisTemplate;
    private ObjectMapper redisObjectMapper;

    private static final long expirationTimeInSeconds = 1800;

    private CategoriesService categoriesService;

    @Autowired
    public CategoryRedisServiceImpl (
            RedisTemplate<String, Object> redisTemplate,
            ObjectMapper redisObjectMapper,
            CategoriesService categoriesService
    ) {
        this.redisTemplate= redisTemplate;
        this.redisObjectMapper = redisObjectMapper;
        this.categoriesService = categoriesService;
    }

    @Override
    public List<CategoriesDto> findAll() throws JsonProcessingException {
        String cacheKey = generateCategoryAllCacheKey();
        String json = (String) redisTemplate.opsForValue().get(cacheKey);
        List<CategoriesDto> categoriesDtoList = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<CategoriesDto>>(){}) : null;
        return categoriesDtoList;
    }

    @Override
    public void saveAll(List<CategoriesDto> categoriesDtoList) {
        String cacheKey = generateCategoryAllCacheKey();

        try {
            String json = redisObjectMapper.writeValueAsString(categoriesDtoList);
            redisTemplate.opsForValue().set(cacheKey, json, expirationTimeInSeconds, TimeUnit.SECONDS);
        } catch(Exception e) {
            logger.info(String.valueOf(e));
        }
    }

    @Override
    public List<CategoriesDto> getLevelOneAndChildren() throws JsonProcessingException {
        String cacheKey = generateLevelOneAndChildren();
        String json = (String) redisTemplate.opsForValue().get(cacheKey);
        List<CategoriesDto> categoriesDtoList = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<CategoriesDto>>(){}) : null;
        return categoriesDtoList;
    }

    @Override
    public void saveLevelOneAndChildren(List<CategoriesDto> categoriesDtoList) {
        String cacheKey = generateLevelOneAndChildren();
        try {
            String json = redisObjectMapper.writeValueAsString(categoriesDtoList);
            redisTemplate.opsForValue().set(cacheKey, json, expirationTimeInSeconds, TimeUnit.SECONDS);
        } catch(Exception e) {
            logger.info(String.valueOf(e));
        }
    }

    @Override
    public CategoriesPageDto getAllCategories(PageRequest pageRequest) throws JsonProcessingException {
        String cacheKey = generatePageCategoryCacheKey(pageRequest);
        String json = (String) redisTemplate.opsForValue().get(cacheKey);

        List<CategoriesDto> categoriesDtoList = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<CategoriesDto>>(){}) : null;
        if(categoriesDtoList != null) {
            long totalCategories = categoriesService.getTotalCategories();
            int totalPages = (int) Math.ceil((double) totalCategories / pageRequest.getPageSize());

            return new CategoriesPageDto(categoriesDtoList, totalPages);
        } else {
            return null;
        }
    }

    @Override
    public void saveAllCategory(List<CategoriesDto> categoriesDtoList, PageRequest pageRequest) {

    }

    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    private String generateCategoryAllCacheKey() {
        return String.format("%s", "categories");
    }

    private String generateLevelOneAndChildren() {
        return String.format("%s", "categories_level");
    }

    private String generatePageCategoryCacheKey( PageRequest pageRequest) {
        return String.format("%s_%d_%d", "categoriesDtoList", pageRequest.getPageNumber(), pageRequest.getPageSize());
    }
}
