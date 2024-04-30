package com.tacm.tabooksapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tacm.tabooksapi.domain.dto.NXBsDto;

import java.util.List;

public interface NXBsRedisService {
    void clear();

    void saveAll(List<NXBsDto> nxBsDtoList);

    List<NXBsDto> findAll() throws JsonProcessingException;
}
