package com.tacm.tabooksapi.domain.entities;

import com.tacm.tabooksapi.service.BookRedisService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class BookListener {
    private final BookRedisService bookRedisService;

    private static final Logger logger = LoggerFactory.getLogger(BookListener.class);

    @PrePersist
    public void prePersit(Books books) {logger.info("prePersist");}

    @PostPersist
    public void postPersist(Books books) {
        logger.info("postPersist");
        bookRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(Books books) {
        logger.info("preUpdate");
    }

    @PostUpdate
    public void postUpdate(Books books) {
        logger.info("postUpdate");
        bookRedisService.clear();
    }

    @PreRemove
    public void preRemove(Books books) {
        logger.info("preRemove");
    }

    @PostRemove
    public void postRemove(Books books) {
        logger.info("postRemove");
        bookRedisService.clear();
    }



}
