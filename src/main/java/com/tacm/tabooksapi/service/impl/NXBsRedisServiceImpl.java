package com.tacm.tabooksapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacm.tabooksapi.domain.dto.CategoriesDto;
import com.tacm.tabooksapi.domain.dto.NXBsDto;
import com.tacm.tabooksapi.service.BookService;
import com.tacm.tabooksapi.service.NXBsRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class NXBsRedisServiceImpl implements NXBsRedisService {
    private static final String NXB_CACHE_PREFIX = "nxb";
    private static final Logger logger = LoggerFactory.getLogger(NXBsRedisServiceImpl.class);
    private RedisTemplate<String, Object> redisTemplate;
    private ObjectMapper redisObjectMapper;

    private static final long expirationTimeInSeconds = 1800;

    @Autowired
    public NXBsRedisServiceImpl(RedisTemplate<String, Object> redisTemplate, ObjectMapper redisObjectMapper) {
        this.redisTemplate = redisTemplate;
        this.redisObjectMapper = redisObjectMapper;
    }


    @Override
    public void saveAll(List<NXBsDto> nxBsDtoList) {
        String cacheKey = generateNXBCacheKey();
        try {
            String json = redisObjectMapper.writeValueAsString(nxBsDtoList);
            redisTemplate.opsForValue().set(cacheKey, json, expirationTimeInSeconds, TimeUnit.SECONDS);
        } catch(Exception e) {
            logger.info(String.valueOf(e));
        }
    }


    @Override
    public List<NXBsDto> findAll() throws JsonProcessingException {
        String cacheKey = generateNXBCacheKey();
        String json = (String) redisTemplate.opsForValue().get(cacheKey);
        List<NXBsDto> nxBsDtoList = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<NXBsDto>>(){}) : null;
        return nxBsDtoList;

    }

    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    public String generateNXBCacheKey() {
        return String.format("%s", NXB_CACHE_PREFIX);
    }
}
