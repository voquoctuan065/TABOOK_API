package com.tacm.tabooksapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacm.tabooksapi.domain.dto.BooksDto;
import com.tacm.tabooksapi.domain.dto.CategoriesDto;
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

    @Autowired
    public CategoryRedisServiceImpl (
            RedisTemplate<String, Object> redisTemplate,
            ObjectMapper redisObjectMapper
    ) {
        this.redisTemplate= redisTemplate;
        this.redisObjectMapper = redisObjectMapper;
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
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    private String generateCategoryAllCacheKey() {
        return String.format("%s", "categories");
    }

    private String generateLevelOneAndChildren() {
        return String.format("%s", "categories_level");
    }
}
