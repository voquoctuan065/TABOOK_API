package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.exception.ApiException;

import java.util.List;

public interface NXBsService {
    List<NXBs> findAll();

    List<NXBs> searchNxbByName(String keyword);

    NXBs addNXB(NXBs nxbs);

    NXBs updateNXB(NXBs nxbs, Long nxbId) throws ApiException;

    NXBs findNxbById(Long nxbId) throws ApiException;

    void deleteNXB(Long nxbId);
}
