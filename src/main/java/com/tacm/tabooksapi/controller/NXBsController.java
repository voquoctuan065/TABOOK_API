package com.tacm.tabooksapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tacm.tabooksapi.domain.dto.NXBsDto;
import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.mapper.impl.NXBsMapper;
import com.tacm.tabooksapi.service.NXBsService;


@RestController
@CrossOrigin("*")
public class NXBsController {
    @Autowired
    private NXBsService nxbsService;
    @Autowired
    private NXBsMapper nxbsMapper;

    @PostMapping(path = "/nxbs")
    public NXBsDto createNXBs(@RequestBody NXBsDto nxbsDto) {
        NXBs nxb = nxbsMapper.mapFrom(nxbsDto);
        NXBs savedNXB = nxbsService.createNXBs(nxb);
        return nxbsMapper.mapTo(savedNXB);
    }

    @GetMapping(path = "/nxbs")
    public List<NXBsDto> listNXBs () {
        List<NXBs> nxbs = nxbsService.findAll();
        return nxbs.stream()
        .map(nxbsMapper::mapTo)
        .collect(Collectors.toList());
    }

    @GetMapping(path = "/nxbs/{id}")
    public ResponseEntity<NXBsDto> getNXBs(@PathVariable("id") Long id) {
        Optional<NXBs> nxbs = nxbsService.findOne(id);
        return nxbs.map(
            nxb -> {
                NXBsDto nxbsDto = nxbsMapper.mapTo(nxb);
                return new ResponseEntity<>(nxbsDto, HttpStatus.OK);
            }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/nxbs/{id}")
    public ResponseEntity<NXBsDto> partialUpdate(
        @PathVariable("id") Long id,
        @RequestBody NXBsDto nxbsDto
    ) {
        if(!nxbsService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        NXBs nxbs = nxbsMapper.mapFrom(nxbsDto);
        NXBs updatedNXBs = nxbsService.partialUpdate(id, nxbs);
        return new ResponseEntity<>(nxbsMapper.mapTo(updatedNXBs), HttpStatus.OK);
    }

    @DeleteMapping("/nxbs/{id}")
    public ResponseEntity deleteNXBs(
        @PathVariable("id") Long id
    ) {
        nxbsService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
