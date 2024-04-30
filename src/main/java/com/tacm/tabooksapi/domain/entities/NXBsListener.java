package com.tacm.tabooksapi.domain.entities;

import com.tacm.tabooksapi.service.CategoryRedisService;
import com.tacm.tabooksapi.service.NXBsRedisService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class NXBsListener {
    private final NXBsRedisService nxBsRedisService;

    private static final Logger logger = LoggerFactory.getLogger(CategoryListener.class);

    @PrePersist
    public void prePersit(NXBs nxBs) {logger.info("prePersist");}

    @PostPersist
    public void postPersist(NXBs nxBs) {
        logger.info("postPersist");
        nxBsRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(NXBs nxBs) {
        logger.info("preUpdate");
    }

    @PostUpdate
    public void postUpdate(NXBs nxBs) {
        logger.info("postUpdate");
        nxBsRedisService.clear();
    }

    @PreRemove
    public void preRemove(NXBs nxBs) {
        logger.info("preRemove");
    }

    @PostRemove
    public void postRemove(NXBs nxBs) {
        logger.info("postRemove");
        nxBsRedisService.clear();
    }
}
