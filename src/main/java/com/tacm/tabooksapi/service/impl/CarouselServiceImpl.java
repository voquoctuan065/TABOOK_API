package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.Carousel;
import com.tacm.tabooksapi.repository.CarouselRepository;
import com.tacm.tabooksapi.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    private CarouselRepository carouselRepository;

    @Autowired
    public CarouselServiceImpl (CarouselRepository carouselRepository) {
        this.carouselRepository = carouselRepository;
    }

    @Override
    public List<Carousel> getCarousel() {
        return carouselRepository.findAll();
    }
}
