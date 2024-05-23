package com.tacm.tabooksapi.controller;

import com.tacm.tabooksapi.domain.dto.CarouselDto;
import com.tacm.tabooksapi.domain.entities.Carousel;
import com.tacm.tabooksapi.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/carousel")
public class CarouselController {
    private CarouselService carouselService;

    @Autowired
    public CarouselController (CarouselService carouselService) {
        this.carouselService = carouselService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<CarouselDto>> getCarousel() {
        List<Carousel> carouselList = carouselService.getCarousel();
        List<CarouselDto> carouselDtoList = carouselList.stream().map(CarouselDto::fromEntity).collect(Collectors.toList());

        return new ResponseEntity<>(carouselDtoList , HttpStatus.OK);
    }
}
