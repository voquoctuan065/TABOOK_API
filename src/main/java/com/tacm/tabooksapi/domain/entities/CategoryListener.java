package com.tacm.tabooksapi.domain.entities;

import com.tacm.tabooksapi.service.CategoryRedisService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class CategoryListener {

    private final CategoryRedisService categoryRedisService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryListener.class);

    @PrePersist
    public void prePersit(Categories categories) {logger.info("prePersist");}

    @PostPersist
    public void postPersist(Categories categories) {
        logger.info("postPersist");
        categoryRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(Categories categories) {
        logger.info("preUpdate");
    }

    @PostUpdate
    public void postUpdate(Categories categories) {
        logger.info("postUpdate");
        categoryRedisService.clear();
    }

    @PreRemove
    public void preRemove(Categories categories) {
        logger.info("preRemove");
    }

    @PostRemove
    public void postRemove(Categories categories) {
        logger.info("postRemove");
        categoryRedisService.clear();
    }
}
