package com.tacm.tabooksapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tacm.tabooksapi.domain.dto.NXBsDto;

import java.util.List;

public interface NXBsRedisService {
    void clear();

    void saveAll(List<NXBsDto> nxBsDtoList);

    List<NXBsDto> findAll() throws JsonProcessingException;

    List<NXBsDto> searchNxbByName(String keyword) throws JsonProcessingException;

    void saveNxbByName(List<NXBsDto> nxBsDtoList, String keyword);

    void saveNxbById(NXBsDto nxBsDto, Long nxbId);

    NXBsDto findNxbById(Long nxbId) throws JsonProcessingException;
}
