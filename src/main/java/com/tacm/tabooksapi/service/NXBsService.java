package com.tacm.tabooksapi.service;

import java.util.List;
import java.util.Optional;

import com.tacm.tabooksapi.domain.entities.NXBs;

public interface NXBsService {

    NXBs createNXBs(NXBs nxb);

    List<NXBs> findAll();

    Optional<NXBs> findOne(Long id);

    NXBs partialUpdate(Long id, NXBs nxbs);

    boolean isExist(Long id);

    void delete(Long id);
    
}
