package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.repository.NXBsRepository;
import com.tacm.tabooksapi.service.NXBsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NXBsServiceImpl implements NXBsService {
    private NXBsRepository nxBsRepository;
    @Autowired
    public NXBsServiceImpl(NXBsRepository nxBsRepository) {
        this.nxBsRepository = nxBsRepository;
    }
    @Override
    public List<NXBs> findAll() {
        return StreamSupport.stream(nxBsRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
