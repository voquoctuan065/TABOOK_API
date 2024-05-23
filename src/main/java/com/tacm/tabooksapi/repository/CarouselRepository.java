package com.tacm.tabooksapi.repository;

import com.tacm.tabooksapi.domain.entities.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselRepository extends JpaRepository<Carousel, Long> {
}
