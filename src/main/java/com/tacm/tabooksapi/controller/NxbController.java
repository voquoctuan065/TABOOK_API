package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.NXBsDto;
import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.mapper.impl.NXBsMapper;
import com.tacm.tabooksapi.service.NXBsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/nxb")
public class NxbController {
    private NXBsMapper nxbMapper;
    private NXBsService nxbService;

    @Autowired
    public NxbController(NXBsMapper nxbMapper, NXBsService nxbService) {
        this.nxbMapper = nxbMapper;
        this.nxbService = nxbService;
    }

    @GetMapping(path = "/nxb/list-nxb")
    public List<NXBsDto> getListNXBs() {
        List<NXBs> nxbList = nxbService.findAll();
        return nxbList.stream().map(nxbMapper::mapTo).collect(Collectors.toList());
    }
}
