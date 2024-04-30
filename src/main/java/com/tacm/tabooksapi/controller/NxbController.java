package com.tacm.tabooksapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tacm.tabooksapi.domain.dto.NXBsDto;
import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.mapper.impl.NXBsMapper;
import com.tacm.tabooksapi.service.NXBsRedisService;
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
    private NXBsRedisService nxBsRedisService;

    @Autowired
    public NxbController(NXBsMapper nxbMapper, NXBsService nxbService, NXBsRedisService nxBsRedisService) {
        this.nxbMapper = nxbMapper;
        this.nxbService = nxbService;
        this.nxBsRedisService = nxBsRedisService;
    }

    @GetMapping(path = "/nxb/list-nxb")
    public List<NXBsDto> getListNXBs() throws JsonProcessingException {
        List<NXBsDto> nxBsDtoList = nxBsRedisService.findAll();
        if(nxBsDtoList == null) {
            List<NXBs> nxbList = nxbService.findAll();
            nxBsDtoList = nxbList.stream().map(nxbMapper::mapTo).collect(Collectors.toList());
            nxBsRedisService.saveAll(nxBsDtoList);
        }
        return nxBsDtoList;
    }
}
